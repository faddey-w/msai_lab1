import sys
import contextlib
from graph import Node, Edge, Graph


def load_from_stream(stream):
    def readline():
        return stream.readline().strip()

    def parse_ints(line_):
        parts = filter(bool, line_.split())
        return map(int, parts)

    num_nodes, num_edges, num_params = parse_ints(readline())
    nodes, edges = [], []
    for _ in range(num_nodes):
        value, num_weights = parse_ints(readline())
        weights = [readline() for _ in range(num_weights)]
        nodes.append(Node(value, tuple(weights)))
    for _ in range(num_edges):
        is_oriented, start, end, num_weights = parse_ints(readline())
        weights = [readline() for _ in range(num_weights)]
        edges.append(Edge(start, end, bool(is_oriented), weights))
    extra = [
        readline() or None
        for _ in range(num_params)
    ]
    return Graph(nodes, edges), extra


def search_demo_script(alhorithm):
    def main():
        with get_stream() as in_stream:
            graph, params = load_from_stream(in_stream)
        start_node = int(params[0])
        target_node = int(params[1] or -1)
        nodes = alhorithm(graph, start_node, target_node)
        values = [n.value for n in nodes]
        print(*values)
    return main


@contextlib.contextmanager
def get_stream():
    if len(sys.argv) > 1:
        with open(sys.argv[1]) as f:
            yield f
    else:
        yield sys.stdin
