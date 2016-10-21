#! python3
import sys
import os


def main():
    path = os.path.abspath(sys.argv[1])
    with open(path, 'w') as out_f:
        out_f.write(sys.stdin.read())
    print("Saved to", path)


if __name__ == '__main__':
    main()
