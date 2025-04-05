import java.util.Scanner;

public class BinarySearchTreeEnhanced {
    // Node class for BST
    static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    private Node root;

    // Constructor
    public BinarySearchTreeEnhanced() {
        root = null;
    }

    // Insert a new node
    public void insert(int data) {
        root = insertRec(root, data);
    }

    // Recursive insert function
    private Node insertRec(Node root, int data) {
        // If tree is empty, create new node
        if (root == null) {
            root = new Node(data);
            return root;
        }

        // Otherwise, recur down the tree
        if (data < root.data)
            root.left = insertRec(root.left, data);
        else if (data > root.data)
            root.right = insertRec(root.right, data);

        // Return unchanged node pointer
        return root;
    }

    // Search for a node
    public boolean search(int data) {
        return searchRec(root, data);
    }

    // Recursive search function
    private boolean searchRec(Node root, int data) {
        // Base case: root is null or key is present at root
        if (root == null)
            return false;
        if (root.data == data)
            return true;

        // Value is greater than root's data
        if (root.data > data)
            return searchRec(root.left, data);

        // Value is less than root's data
        return searchRec(root.right, data);
    }

    // Delete a node
    public void delete(int data) {
        root = deleteRec(root, data);
    }

    // Recursive delete function
    private Node deleteRec(Node root, int data) {
        // Base case: If the tree is empty
        if (root == null)
            return root;

        // Recursive calls for ancestors of node to be deleted
        if (data < root.data)
            root.left = deleteRec(root.left, data);
        else if (data > root.data)
            root.right = deleteRec(root.right, data);
        else {
            // Node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // Node with two children: Get the inorder successor (smallest in right subtree)
            root.data = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.data);
        }

        return root;
    }

    // Find the minimum value in the BST
    public int findMin() {
        if (root == null)
            throw new IllegalStateException("Tree is empty");
        return minValue(root);
    }

    // Helper function to find minimum value
    private int minValue(Node root) {
        int minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }

    // Find the maximum value in the BST
    public int findMax() {
        if (root == null)
            throw new IllegalStateException("Tree is empty");
        return maxValue(root);
    }

    // Helper function to find maximum value
    private int maxValue(Node root) {
        int maxValue = root.data;
        while (root.right != null) {
            maxValue = root.right.data;
            root = root.right;
        }
        return maxValue;
    }

    // Calculate height of tree
    public int height() {
        return heightRec(root);
    }

    // Recursive function to calculate height
    private int heightRec(Node root) {
        if (root == null)
            return -1;
        else {
            int leftHeight = heightRec(root.left);
            int rightHeight = heightRec(root.right);

            if (leftHeight > rightHeight)
                return leftHeight + 1;
            else
                return rightHeight + 1;
        }
    }

    // Inorder traversal
    public void inorder() {
        System.out.print("Inorder Traversal: ");
        inorderRec(root);
        System.out.println();
    }

    // Recursive inorder traversal
    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }

    // Preorder traversal
    public void preorder() {
        System.out.print("Preorder Traversal: ");
        preorderRec(root);
        System.out.println();
    }

    // Recursive preorder traversal
    private void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    // Postorder traversal
    public void postorder() {
        System.out.print("Postorder Traversal: ");
        postorderRec(root);
        System.out.println();
    }

    // Recursive postorder traversal
    private void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.data + " ");
        }
    }

    // Check if BST is empty
    public boolean isEmpty() {
        return root == null;
    }

    // Enhanced tree visualization
    public void visualizeTree() {
        System.out.println("\n===== Binary Search Tree Visualization =====");
        if (root == null) {
            System.out.println("Tree is empty!");
            return;
        }

        int height = height();
        int totalLevels = height + 1;
        
        // Prepare array to store nodes at each level
        String[][] treeArray = new String[totalLevels][calculateMaxWidth(totalLevels)];
        for (int i = 0; i < treeArray.length; i++) {
            for (int j = 0; j < treeArray[i].length; j++) {
                treeArray[i][j] = " ";
            }
        }
        
        // Fill the array with node values
        fillTreeArray(root, 0, 0, treeArray[0].length - 1, treeArray);
        
        // Print the tree
        for (String[] level : treeArray) {
            for (String node : level) {
                System.out.print(node);
            }
            System.out.println();
        }
        
        System.out.println("=========================================");
    }

    // Calculate maximum width for the array based on tree height
    private int calculateMaxWidth(int height) {
        // For perfect binary tree, max nodes at lowest level is 2^h
        // We need more space for formatting
        return (int) Math.pow(2, height + 1) * 2;
    }

    // Fill the array with node values at appropriate positions
    private void fillTreeArray(Node node, int level, int left, int right, String[][] treeArray) {
        if (node == null) return;
        
        int mid = (left + right) / 2;
        
        // Format the node value with padding
        String nodeStr = formatNodeValue(node.data);
        
        // Place node value at the middle position
        treeArray[level][mid] = nodeStr;
        
        // Draw branches to children if they exist
        if (node.left != null || node.right != null) {
            // Left branch
            if (node.left != null) {
                int nextMid = (left + mid) / 2;
                // Draw left branch
                for (int i = mid - 1; i > nextMid; i--) {
                    treeArray[level + 1][i] = "/";
                }
            }
            
            // Right branch
            if (node.right != null) {
                int nextMid = (mid + right) / 2;
                // Draw right branch
                for (int i = mid + nodeStr.length(); i < nextMid; i++) {
                    treeArray[level + 1][i] = "\\";
                }
            }
        }
        
        // Recursively fill in children
        fillTreeArray(node.left, level + 2, left, mid - 1, treeArray);
        fillTreeArray(node.right, level + 2, mid + 1, right, treeArray);
    }

    // Format node value with consistent width
    private String formatNodeValue(int value) {
        String nodeStr = Integer.toString(value);
        // Ensure node has at least 3 characters width for better spacing
        if (nodeStr.length() < 2) {
            nodeStr = " " + nodeStr + " ";
        } else if (nodeStr.length() < 3) {
            nodeStr = nodeStr + " ";
        }
        return nodeStr;
    }

    // Alternative detailed tree view for better visualization
    public void printDetailedTree() {
        System.out.println("\n===== Binary Search Tree Structure =====");
        if (root == null) {
            System.out.println("Tree is empty!");
            return;
        }
        
        // Get the height of the tree
        int height = height();
        
        // Create a horizontal view of the tree using array
        String[][] levelView = new String[2 * height + 1][];
        for (int i = 0; i < levelView.length; i++) {
            levelView[i] = new String[calculateLevelWidth(height)];
            for (int j = 0; j < levelView[i].length; j++) {
                levelView[i][j] = " ";
            }
        }
        
        // Fill the level view
        fillLevelView(root, 0, 0, levelView[0].length, levelView, 0);
        
        // Print the level view
        for (String[] row : levelView) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
        
        System.out.println("=====================================");
    }
    
    // Calculate width for level display
    private int calculateLevelWidth(int height) {
        // Width estimation for a balanced tree
        return (int) Math.pow(2, height + 1) - 1;
    }
    
    // Fill the level view array
    private void fillLevelView(Node node, int row, int left, int right, String[][] levelView, int level) {
        if (node == null) return;
        
        int mid = (left + right) / 2;
        
        // Put the node value
        levelView[row][mid] = Integer.toString(node.data);
        
        // Connect to left child
        if (node.left != null) {
            // Draw connector line
            for (int i = 1; i < (right - left) / 4; i++) {
                levelView[row + 1][mid - i] = "/";
            }
            fillLevelView(node.left, row + 2, left, mid, levelView, level + 1);
        }
        
        // Connect to right child
        if (node.right != null) {
            // Draw connector line
            for (int i = 1; i < (right - left) / 4; i++) {
                levelView[row + 1][mid + i] = "\\";
            }
            fillLevelView(node.right, row + 2, mid, right, levelView, level + 1);
        }
    }

    // Main method containing user interface
    public static void main(String[] args) {
        BinarySearchTreeEnhanced bst = new BinarySearchTreeEnhanced();
        Scanner scanner = new Scanner(System.in);
        int choice, value;

        System.out.println("Binary Search Tree Operations");
        System.out.println("-----------------------------");

        // Create initial BST
        System.out.println("Enter numbers to insert into BST (enter -1 to stop):");
        while (true) {
            value = scanner.nextInt();
            if (value == -1)
                break;
            bst.insert(value);
            // Show tree after each insertion (optional)
            // bst.visualizeTree();
        }

        // Menu for operations
        do {
            System.out.println("\nBST Operations Menu:");
            System.out.println("1. Insert a number");
            System.out.println("2. Delete a number");
            System.out.println("3. Search for a number");
            System.out.println("4. Display inorder traversal");
            System.out.println("5. Display preorder traversal");
            System.out.println("6. Display postorder traversal");
            System.out.println("7. Find minimum value");
            System.out.println("8. Find maximum value");
            System.out.println("9. Calculate height of the tree");
            System.out.println("10. Visualize tree (Primary)");
            System.out.println("11. Visualize tree (Alternative)");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the number to insert: ");
                    value = scanner.nextInt();
                    bst.insert(value);
                    System.out.println(value + " inserted successfully.");
                    break;
                case 2:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        System.out.print("Enter the number to delete: ");
                        value = scanner.nextInt();
                        if (bst.search(value)) {
                            bst.delete(value);
                            System.out.println(value + " deleted successfully.");
                        } else {
                            System.out.println(value + " not found in the tree.");
                        }
                    }
                    break;
                case 3:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        System.out.print("Enter the number to search: ");
                        value = scanner.nextInt();
                        if (bst.search(value)) {
                            System.out.println(value + " found in the tree.");
                        } else {
                            System.out.println(value + " not found in the tree.");
                        }
                    }
                    break;
                case 4:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        bst.inorder();
                    }
                    break;
                case 5:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        bst.preorder();
                    }
                    break;
                case 6:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        bst.postorder();
                    }
                    break;
                case 7:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        System.out.println("Minimum value: " + bst.findMin());
                    }
                    break;
                case 8:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        System.out.println("Maximum value: " + bst.findMax());
                    }
                    break;
                case 9:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        System.out.println("Height of the tree: " + bst.height());
                    }
                    break;
                case 10:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        bst.visualizeTree();
                    }
                    break;
                case 11:
                    if (bst.isEmpty()) {
                        System.out.println("Tree is empty!");
                    } else {
                        bst.printDetailedTree();
                    }
                    break;
                case 12:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 12);

        scanner.close();
    }
}