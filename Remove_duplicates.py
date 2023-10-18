# Input array from the user
array_input = input("Enter elements of the array separated by space: ")

# Split the input string into a list
array = list(map(int, array_input.split()))

# Remove duplicates
array_without_duplicates = list(set(array))

# Print the array without duplicates
print("Array without duplicates:", array_without_duplicates)
