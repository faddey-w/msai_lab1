#! python3
import logging
logging.basicConfig(filename='script.log',
                    level=logging.DEBUG)

logging.info("I'm up!")


def main():
    from graph_io import get_stream, load_from_stream
    with get_stream() as stream:
        graph, _ = load_from_stream(stream)
    not_weighted = [e for e in graph.edges if not e.weights]
    if not_weighted:
        print('Не взвешены:')
        for e in not_weighted:
            print('{}-{}'.format(e.start_node, e.end_node))
    else:
        print('Все ребра взвешены')


if __name__ == '__main__':
    try:
        main()
    except:
        import traceback
        logging.error(traceback.format_exc())
