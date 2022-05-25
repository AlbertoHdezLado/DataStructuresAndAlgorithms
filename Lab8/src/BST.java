import java.util.NoSuchElementException;

public class BST<T extends Comparable> {
    
        int size;
        private T removedValue;
    
	private class Node{
		T value;
		Node left,right,parent;
                
		public Node(T v) {
			value=v;
		}
		public Node(T value, Node left, Node right, Node parent) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}		
	private Node root=null;

	public BST() {
	}

        private Node getElementRecursive(Node current, T value)
        {
            if (current == null)
            {
                return null;
            }
            if (value.compareTo(current.value) == 0)
            {
                return current;
            }
            return value.compareTo(current.value) < 0 ? getElementRecursive(current.left, value) : getElementRecursive(current.right, value);
        }
        
        
        
	public T getElement(T toFind) {
                // TODO
                Node result = getElementRecursive(root, toFind);
                if (result == null)
                {
                    return null;
                }
                return result.value;
	}

	public T successor(T elem) {
		// TODO
		Node succNode = successorNode(root, elem);
                return succNode == null ? null : succNode.value;
	}

      
        
        private Node successorNode(Node node, T elem)
        {
            if (node == null)
            {
                return null;
            }
            int cmp = elem.compareTo(node.value);
            if (cmp == 0)
            {
                if (node.right != null)
                {
                    return getMin(node.right);
                }
                else
                {
                    return null;
                }
            }
            else
            {
                if (cmp < 0)
                {
                    Node retNode = successorNode(node.left, elem);
                    return retNode == null ? node : retNode;
                }
                else
                {
                    return successorNode(node.right, elem);
                }
            }
        }
        
        
        
        private void traversePreOrder(Node node, StringBuilder result)
        {
            if (node != null)
            {
                result.append(node.value).append(", ");
                traversePreOrder(node.left, result);
                traversePreOrder(node.right, result);
            }
        }
        
        public static String getTraverseResult(StringBuilder builder)
        {
            if (builder.length() == 0)
            {
                return builder.toString();
            }
            return builder.substring(0, builder.length() - 2);
        }
        

	public String toStringInOrder() {
		// TODO
		StringBuilder result = new StringBuilder();
                traverseInOrder(root, result);
                return getTraverseResult(result);
	}
        
        public void traverseInOrder(Node node, StringBuilder result)
        {
            if (node != null)
            {
                traverseInOrder(node.left, result);
                result.append(node.value).append(", ");
                traverseInOrder(node.right, result);
            }
        }


	public String toStringPreOrder() {
		// TODO
		StringBuilder result = new StringBuilder();
                traversePreOrder(root, result);
                return getTraverseResult(result);
	}
           
        
        private void traversePostOrder(Node node, StringBuilder result)
        {
            if (node != null)
            {
                traversePostOrder(node.left, result);
                traversePostOrder(node.right, result);
                result.append(node.value).append(", ");
            }
        }        
        
	public String toStringPostOrder() {
		// TODO
		StringBuilder result = new StringBuilder();
                traversePostOrder(root, result);
                return getTraverseResult(result);
	}


	public boolean add(T elem) {
		// TODO
		int oldSize = size;
                root = addRecursive(root, elem);
                return oldSize != size;
	}

        
        private Node addRecursive(Node current, T value)
        {
            if (current == null)
            {
                size++;
                return new Node(value);
            }

            if (current.value.compareTo(value) > 0)
            {
                current.left = addRecursive(current.left, value);
            }
            else
            {
                if (current.value.compareTo(value) < 0)
                {
                    current.right = addRecursive(current.right, value);
                }
                else
                {
                    return current;
                }
            }

            return current;
        }
        
        
        private Node getMin(Node root)
        {
                return root.left == null ? root : getMin(root.left);
        }


        
        protected Node remove(T elem, Node node)
        {
            if (node == null)
            {
                return null;
            }
            else
            {
                int cmp = elem.compareTo(node.value);
                if (cmp < 0)
                {
                    node.left = remove(elem, node.left);
                }
                else
                {
                    if (cmp > 0)
                    {
                        node.right = remove(elem, node.right);
                    }
                    else
                    {
                        if (node.left != null && node.right != null)
                        {
                            node.right = detachMin(node, node.right);
                        }
                        else
                        {
                            removedValue = node.value;
                            node = (node.left != null) ? node.left : node.right;
                            size--;
                        }
                    }
                }
            }
            return node;
        }
        
        
        private Node detachMin(Node del, Node node)
        {
            if (node.left != null)
            {
                node.left = detachMin(del, node.left);
            }
            else
            {
                size--;
                removedValue = del.value;
                del.value = node.value;
                node = node.right;
            }
            return node;
        }
        
        
	public T remove(T value) {
		// TODO
                removedValue = null;
                root = remove(value, root);
                return removedValue;
        }
	
	public void clear() {
		// TODO
                root = null;
                size = 0;
	}

	public int size() {
		// TODO
		return size;
	}

}