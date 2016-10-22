from collections import namedtuple


class Node(namedtuple('GraphNode', 'value weights')):

    def __eq__(self, other):
        if isinstance(other, Node):
            return self.value ==  other.value
        else:
            return self.value == other

    def __hash__(self):
        return hash((self.value, tuple(self.weights)))


class Edge(namedtuple('GraphEdge', 'start_node end_node is_oriented weights')):

    def __eq__(self, other):
        if self.is_oriented != other.is_oriented:
            return False
        coll_t = tuple if self.is_oriented else set
        return coll_t((self.start_node, self.end_node)) \
               == coll_t((other.start_node, other.end_node))

    def __hash__(self):
        return hash((self.start_node, self.end_node, self.is_oriented,
                     tuple(self.weights)))


SearchNode = namedtuple('SearchNode', 'graph_node edge parent_node depth')


class Graph:

    def __init__(self, nodes, edges):
        self.nodes = tuple(nodes)
        self.edges = tuple(edges)

        # all node values are unique
        assert len(set(n.value for n in self.nodes)) == len(self.nodes)

        # all edges connect existing nodes
        assert set(n.value for n in self.nodes).issuperset(
            set(e.start_node for e in self.edges)
            | set(e.end_node for e in self.edges)
        )

        self._move_map = {
            node.value: sorted(
                e.end_node if e.start_node == node else e.start_node
                for e in self.edges
                if e.start_node == node or (
                    not e.is_oriented and e.end_node == node
                )
            )
            for node in self.nodes
        }
        self._value2node = {
            node.value: node
            for node in self.nodes
        }
        self._pair2edge = {}
        for edge in self.edges:
            self._pair2edge[(edge.start_node, edge.end_node)] = edge
            if not edge.is_oriented:
                self._pair2edge[(edge.end_node, edge.start_node)] = edge

    def get_edge(self, start_node, end_node):
        return self._pair2edge[
            self[start_node].value,
            self[end_node].value,
        ]

    def get_outgoing(self, node, with_edges=False):
        node_value = self[node].value
        out_values = self._move_map.get(node_value, [])
        if with_edges:
            return [
                (self._pair2edge[node_value, val], self._value2node[val])
                for val in out_values
            ]
        else:
            return [
                self._value2node[val]
                for val in out_values
            ]

    def __getitem__(self, value):
        if isinstance(value, Node):
            value = value.value
        return self._value2node[value]

    def __contains__(self, value):
        return value in self.nodes


def graph_traverse_algorithm(queue_strategy):
    def traverse_graph(graph, start_node, limit_depth=float('inf')):
        if start_node not in graph:
            return
        start_node = graph[start_node]
        queue = [SearchNode(start_node, None, None, 0)]
        while queue:
            search_node = queue.pop(0)
            yield search_node
            next_depth = search_node.depth + 1
            if next_depth > limit_depth:
                continue
            next_nodes = [
                SearchNode(n, e, search_node, next_depth)
                for e, n in graph.get_outgoing(
                    search_node.graph_node, with_edges=True
                )
            ]
            queue_strategy(queue, next_nodes)
    return traverse_graph


def graph_visit_algorithm(traverse_algorithm):
    def visit_graph(graph, start_node, stop_node):
        visited_nodes = []
        for search_node in traverse_algorithm(graph, start_node):
            node = search_node.graph_node
            if node not in visited_nodes:
                visited_nodes.append(node)
            if node == stop_node:
                break
        return visited_nodes
    return visit_graph


def graph_search_algorithm(traverse_algorithm):
    def search_in_graph(graph, start_node, target_node):
        for search_node in traverse_algorithm(graph, start_node):
            if search_node.graph_node == target_node:
                reversed_path = []
                sn = search_node
                while sn is not None:
                    reversed_path.append(sn.edge)
                    sn = sn.parent_node
                return reversed_path[::-1]
    return search_in_graph


def breadth_first_strategy(queue, search_nodes):
    queue.extend(search_nodes)
breadth_first_traverse = graph_traverse_algorithm(breadth_first_strategy)
breadth_first_search_demo = graph_visit_algorithm(breadth_first_traverse)
breadth_first_search = graph_search_algorithm(breadth_first_traverse)


def depth_first_strategy(queue, search_nodes):
    queue[0:0] = search_nodes
depth_first_traverse = graph_traverse_algorithm(depth_first_strategy)
depth_first_search_demo = graph_visit_algorithm(depth_first_traverse)
depth_first_search = graph_search_algorithm(depth_first_traverse)
