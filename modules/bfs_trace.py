from graph import graph_traverse_algorithm, graph_search_algorithm
from graph_gen import build_graph


def insert_breadth_first_with_trace(queue, search_nodes):
    print('Добавляем:', ', '.join(
        str(sn.graph_node.value)
        for sn in search_nodes
    ))
    queue.extend(search_nodes)
    print('Очередь:', ', '.join(
        str(sn.graph_node.value)
        for sn in queue
    ))


def goal_test(target_nodes):
    def is_goal(node):
        print('\nПроверяем', node.value)
        if node in target_nodes:
            print('Целевой узел найден!')
            return True
        return False
    return is_goal


trace_search = graph_search_algorithm(graph_traverse_algorithm(
    insert_breadth_first_with_trace
))


def main():
    graph = build_graph(1, [
        lambda n: 6*n + 1,
        lambda n: 6*n + 3,
        lambda n: 6*n + 5,
    ], max_depth=4)
    trace_search(graph, 1, goal_test([261, 67, 287]))


if __name__ == '__main__':
    main()
