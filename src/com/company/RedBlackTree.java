package com.company;

public class RedBlackTree {
    // 1. Every node is red or black
    // 2. Root is always black
    // 3. New insertions are always red
    // 4. Every path has the same number of black nodes
    // 5. no path has 2 consecutive red nodes
    // 6. nulls are black

    private class RBNode {
        int data;
        RBNode left;
        RBNode right;
        RBNode parent;
        boolean isBlack;
        public RBNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.isBlack = false;
            this.parent = null;
        }

        @Override
        public String toString() {
            return "[" + data + "]";
        }
    }

    RBNode root;
    int blackHeight;
    public RedBlackTree() {
        root = null;
        blackHeight = 0;
    }

    public void insert(int data) {
        System.out.println("Insert " + data);
        // the inserted node
        RBNode inserted = new RBNode(data);

        if(root == null) {
            root = inserted;
            inserted.parent = null; // (remains null)
        }
        else {
            System.out.println("Placing into tree");
            RBNode runner = root;
            while(true) {
                System.out.println("Runner:" + runner);
                // runner data too big, go left
                if(runner.data > data) {
                    // no left
                    if(runner.left == null) {
                        runner.left = inserted;
                        inserted.parent = runner;
                        break;
                    }
                    else {
                        runner = runner.left;
                    }
                }
                else {
                    // no right
                    if(runner.right == null) {
                        runner.right = inserted;
                        inserted.parent = runner;
                        break;
                    }
                    else {
                        runner = runner.right;
                    }
                }
            }
        }

        System.out.println("Checking color balance");
        // inserted is now in the tree
        while(true) {
            System.out.println("Balancing " + inserted);

            if(inserted == root) {
                inserted.isBlack = true;
                blackHeight++;
                break;
            }

            // parent can not be null if not the root
            RBNode parent = inserted.parent;

            if (parent.isBlack == false) {
                // parent is red, parent is not root
                // (root is always black)
                RBNode grandparent = parent.parent;
                // uncle is either grandparents right or left, assume right
                RBNode uncle = grandparent.right;
                // if the right child of grandparent is insert's parent
                if (uncle == parent) {
                    // then uncle must have been the left child instead
                    uncle = grandparent.left;
                }


                if (uncle != null && uncle.isBlack == false) {
                    // uncle exists and is red
                    uncle.isBlack = true;
                    parent.isBlack = true;
                    grandparent.isBlack = false;
                    inserted = grandparent;
                    // repeat
                }
                else if(uncle == null || uncle.isBlack == true) {
                    // uncle is black or doesnt exist (null is black)

                    // left left or leftright or rightright or rightleft
                    if(grandparent.left != null && grandparent.left.left == inserted) {
                        // left left
                        // x and uncle's children do not change

                        // update and link grandparent left to T3, then recolor
                        grandparent.left = parent.right;
                        if(grandparent.left != null) {
                            grandparent.left.parent = grandparent;
                        }
                        grandparent.isBlack = false;

                        // update and link parent right to grandparent, then recolor
                        parent.right = grandparent;
                        grandparent.parent = parent;
                        parent.isBlack = true;

                        // if grandparent was the root, the parent is now the root
                        if(grandparent == root) {
                            root = parent;
                        }

                    }
                    else if(grandparent.left != null && grandparent.left.right == inserted) {
                        // left right
                        // uncle child unchanged, x and p swap, then whole tree rotates

                        // update parent right to T2 and relink, parent does not change color
                        parent.right = inserted.left;
                        if(parent.right != null) {
                            parent.right.parent = parent;
                        }

                        // update grandparent left to T3 and relink and recolor
                        grandparent.left = inserted.right;
                        if(grandparent.left != null) {
                            grandparent.left.parent = grandparent;
                        }
                        grandparent.isBlack = false;

                        // update X node's children to parent/grandparent, relink, and recolor
                        inserted.left = parent;
                        inserted.right = grandparent;
                        parent.parent = inserted;
                        grandparent.parent = inserted;
                        inserted.isBlack = true;

                        if(grandparent == root) {
                            root = inserted;
                        }
                    }
                    else if(grandparent.right != null && grandparent.right.right == inserted) {
                        // right right
                        // x and uncle's children do not change

                        // update and link grandparent right to T3, then recolor
                        grandparent.right = parent.left;
                        if(grandparent.right != null) {
                            grandparent.right.parent = grandparent;
                        }
                        grandparent.isBlack = false;

                        // update and link parent left to grandparent, then recolor
                        parent.left = grandparent;
                        grandparent.parent = parent;
                        parent.isBlack = true;

                        // if grandparent was the root, the parent is now the root
                        if(grandparent == root) {
                            root = parent;
                        }

                    }
                    else if(grandparent.right != null && grandparent.right.left == inserted) {
                        // right left

                        // uncle child unchanged, x and p swap, then whole tree rotates

                        // update parent right to T4 and relink, parent does not change color
                        parent.left = inserted.right;
                        if(parent.left != null) {
                            parent.left.parent = parent;
                        }

                        // update grandparent right to T3 and relink and recolor
                        grandparent.right = inserted.left;
                        if(grandparent.right != null) {
                            grandparent.right.parent = grandparent;
                        }
                        grandparent.isBlack = false;

                        // update X node's children to parent/grandparent, relink, and recolor
                        inserted.left = grandparent;
                        inserted.right = parent;
                        parent.parent = inserted;
                        grandparent.parent = inserted;
                        inserted.isBlack = true;

                        if(grandparent == root) {
                            root = inserted;
                        }
                    }
                    break;
                }
            }
            else {
                break;
            }
        }
    }

    void inOrderPrint() {
        System.out.println("PRINTING");
        inOrderPrintHelper(root);
    }

    private void inOrderPrintHelper(RBNode current) {
        if(current == null) {
            return;
        }
        inOrderPrintHelper(current.left);
        System.out.print(current.data + " ");
        inOrderPrintHelper(current.right);
    }


}
