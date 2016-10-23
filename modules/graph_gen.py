"""
A simple script that generates the graph by given constraints and
outputs it as image file
"""

import sys
import math
from PIL import Image, ImageDraw, ImageFont
from collections import namedtuple
import graph


def build_graph(start_value, transition_functions, max_depth):
    nodes = {start_value: graph.Node(start_value, ())}
    edges = set()

    def _add_next_recursive(prev_value, depth):
        if depth > max_depth:
            return
        for func in transition_functions:
            value = func(prev_value)
            nodes[value] = graph.Node(value, ())
            edges.add(graph.Edge(prev_value, value, True, ()))
            _add_next_recursive(value, depth+1)

    _add_next_recursive(start_value, 0)

    return graph.Graph(nodes.values(), edges)


NodePosition = namedtuple('NodePosition', 'node x y')


def arrange_circular(graph_, start_node, neighbor_distance, arrow_distance):
    """
    Arranges graph nodes as circles with start node in the center,
    level by level. Assumes that graph is a tree.
    :return: List[NodePosition]
    """
    positions = [NodePosition(graph_[start_node], 0, 0)]
    current_radius = 0
    front = graph_.get_outgoing(start_node)
    while front:
        nodes_cnt = len(front)
        min_radius = current_radius + arrow_distance
        possible_distance = min_radius * math.sin(math.pi / nodes_cnt)
        if possible_distance >= neighbor_distance or nodes_cnt == 1:
            current_radius = min_radius
        else:
            current_radius = min_radius * neighbor_distance / possible_distance
        angle_step = 2 * math.pi / nodes_cnt
        for i, node in enumerate(front):
            positions.append(NodePosition(
                node,
                current_radius * math.cos((i+0.5) * angle_step),
                current_radius * math.sin((i+0.5) * angle_step),
            ))
        front = [
            n
            for front_node in front
            for n in graph_.get_outgoing(front_node)
        ]
    return positions


def translate(positions, dx, dy):
    return [
        NodePosition(np.node, np.x + dx, np.y + dy)
        for np in positions
    ]


def reduce_attr(func, attr):
    return lambda items: func(getattr(item, attr) for item in items)
max_x = reduce_attr(max, 'x')
min_x = reduce_attr(min, 'x')
max_y = reduce_attr(max, 'y')
min_y = reduce_attr(min, 'y')
max_value = reduce_attr(max, 'value')
min_value = reduce_attr(min, 'value')


def draw_graph(graph_, arrange_func):
    arrow_size = 15
    start_node = min_value(graph_.nodes)
    circle_radius = hyp(*get_pixel_size(str(max_value(graph_.nodes)))) / 2 + 1
    positions = arrange_func(
        graph_, start_node,
        1.1*circle_radius,
        2*circle_radius + arrow_size + 3)

    dx, dy = -min_x(positions), -min_y(positions)
    positions = translate(positions, dx+circle_radius*2, dy+circle_radius*2)

    im_w, im_h = max_x(positions), max_y(positions)
    im_w = int(math.ceil(im_w)+circle_radius*2)
    im_h = int(math.ceil(im_h)+circle_radius*2)
    image = Image.new('RGB', (im_w, im_h), 'white')
    draw = ImageDraw.Draw(image)

    for np in positions:
        draw.ellipse(
            [np.x-circle_radius, np.y-circle_radius,
             np.x+circle_radius, np.y+circle_radius],
            fill='black', outline='black'
        )
        value_str = str(np.node.value)
        val_w, val_h = get_pixel_size(value_str)
        draw.text(
            (np.x-val_w/2, np.y-val_h/2),
            value_str, fill='white'
        )
    value2np = {np.node.value: np for np in positions}
    for edge in graph_.edges:
        start_pos = value2np[edge.start_node]
        end_pos = value2np[edge.end_node]
        incline_sin = (end_pos.x-start_pos.x) / dist(start_pos, end_pos)
        incline_cos = (end_pos.y-start_pos.y) / dist(start_pos, end_pos)
        dx = incline_sin * circle_radius
        dy = incline_cos * circle_radius
        [start_pos] = translate([start_pos], dx, dy)
        [end_pos] = translate([end_pos], -dx, -dy)
        draw.line([
            (start_pos.x, start_pos.y),
            (end_pos.x, end_pos.y)
        ], fill='black', width=2)
        arrow_sin, arrow_cos = rotate(incline_cos, incline_sin, math.pi / 12)
        draw.line([
            (end_pos.x - arrow_size*arrow_sin, end_pos.y - arrow_size*arrow_cos),
            (end_pos.x, end_pos.y)
        ], fill='black', width=2)
        arrow_sin, arrow_cos = rotate(incline_cos, incline_sin, -math.pi / 12)
        draw.line([
            (end_pos.x - arrow_size*arrow_sin, end_pos.y - arrow_size*arrow_cos),
            (end_pos.x, end_pos.y)
        ], fill='black', width=2)

    return image


def get_pixel_size(string):
    return _default_font.getsize(string)
_default_font = ImageFont.load_default()


def hyp(a, b):
    return math.sqrt(a*a + b*b)


def dist(n_pos1, n_pos2):
    return hyp(n_pos1.x-n_pos2.x, n_pos1.y-n_pos2.y)


def rotate(sin, cos, angle):
    cos_a, sin_a = math.cos(angle), math.sin(angle)
    return (
        cos*cos_a - sin*sin_a,
        cos*sin_a + sin*cos_a
    )


def main():
    filename = sys.argv[1]
    g = build_graph(1, [
        lambda n: 6*n + 1,
        lambda n: 6*n + 3,
        lambda n: 6*n + 5,
    ], max_depth=4)
    img = draw_graph(g, arrange_circular)
    img.save(filename)


if __name__ == '__main__':
    main()
