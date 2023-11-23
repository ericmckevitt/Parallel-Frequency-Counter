import sys
import random
import string

def generate_random_characters(length):
    random_chars = [random.choice(string.ascii_lowercase) for _ in range(length)]
    return random_chars

if len(sys.argv) != 2:
    print(f"Usage: python {sys.argv[0]} <length>")
    sys.exit(1)
try:
    length = int(sys.argv[1])

    if length < 0:
        print("Please enter a non-negative integer.")
        sys.exit(1)

    random_list = generate_random_characters(length)
    print(''.join(random_list))
except ValueError:
    print("Please enter a valid integer.")
    sys.exit(1)