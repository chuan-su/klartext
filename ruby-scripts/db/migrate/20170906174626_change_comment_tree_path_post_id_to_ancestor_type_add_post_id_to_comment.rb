class ChangeCommentTreePathPostIdToAncestorTypeAddPostIdToComment < ActiveRecord::Migration[5.1]
  def change
    remove_column :comment_tree_paths,:post_id
    add_column :comment_tree_paths,:ancestor_type,:string
    add_column :comments,:post_id,:integer
  end
end
