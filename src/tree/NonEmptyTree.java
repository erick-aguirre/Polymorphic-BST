package tree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 * 
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	/* Provide whatever instance variables you need */
	K key;
	V value;
	Tree<K, V> left;
	Tree<K, V> right;
	Tree<K, V> root;

	/**
	 * Only constructor we need.
	 * 
	 * @param key
	 * @param value
	 * @param left
	 * @param right
	 */
	public NonEmptyTree(K key, V value, Tree<K, V> left, Tree<K, V> right) {

		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;

	}

	public V search(K key) {

		int result = key.compareTo(this.key);

		if (result == 0) {

			return this.value;
		} else if (result > 0) {

			return right.search(key);

		} else {

			return left.search(key);

		}

	}

	public NonEmptyTree<K, V> insert(K key, V value) {

		int result = key.compareTo(this.key);

		if (result == 0) {
			this.key = key;
			this.value = value;
			return this;
		} else if (result > 0) {

			this.right = right.insert(key, value);
			return this;

		} else {
			this.left = left.insert(key, value);
			return this;

		}

	}

	public Tree<K, V> delete(K key) {

		int result = key.compareTo(this.key);

		if (result == 0) {

			try {

				key.compareTo(left.max());
				key.compareTo(right.max());

				this.value = search(left.max());
				this.key = left.max();
				left = left.delete(left.max());

			} catch (TreeIsEmptyException e) {

				try {

					key.compareTo(left.max());
					this.value = search(left.max());
					this.key = left.max();

					left = left.delete(left.max());

				} catch (TreeIsEmptyException c) {

					try {

						key.compareTo(right.max());
						this.value = search(right.min());
						this.key = right.min();
						right = right.delete(right.min());

					} catch (TreeIsEmptyException g) {

						return EmptyTree.getInstance();

					}
				}
			}
		} else if (result > 0) {

			right = right.delete(key);

		} else if (result < 0) {

			left = left.delete(key);

		}

		return this;
	}

	public K max() {

		try {
			return right.max();

		} catch (TreeIsEmptyException e) {

			return this.key;
		}

	}

	public K min() {

		try {

			return left.min();

		} catch (TreeIsEmptyException e) {

			return this.key;

		}
	}

	public int size() {

		int rightSize = right.size() + 1;

		int leftSize = left.size();

		return rightSize + leftSize;

	}

	public void addKeysToCollection(Collection<K> c) {

		left.addKeysToCollection(c);

		c.add(key);

		right.addKeysToCollection(c);

	}

	public Tree<K, V> subTree(K fromKey, K toKey) {

		int result = this.key.compareTo(fromKey);
		int resultTwo = this.key.compareTo(toKey);

		if (result >= 0 && resultTwo <= 0) {

			return new NonEmptyTree<K, V>(key, value, left.subTree(fromKey, toKey), right.subTree(fromKey, toKey));

		} else if (result >= 0 && resultTwo > 0) {

			return left.subTree(fromKey, toKey);

		} else if (result < 0 && resultTwo <= 0) {

			return right.subTree(fromKey, toKey);
		} else {

			return EmptyTree.getInstance();

		}

	}

	public int height() {

		int leftHeight = left.height() + 1;

		int rightHeight = right.height() + 1;

		if (leftHeight > rightHeight) {

			return leftHeight;

		} else {

			return rightHeight;

		}

	}

	public void inorderTraversal(TraversalTask<K, V> p) {

		left.inorderTraversal(p);

		p.performTask(key, value);

		right.inorderTraversal(p);

	}

	public void rightRootLeftTraversal(TraversalTask<K, V> p) {

		right.rightRootLeftTraversal(p);

		p.performTask(key, value);

		left.rightRootLeftTraversal(p);

	}

}