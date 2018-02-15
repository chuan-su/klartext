class AddColumnDescendantType < ActiveRecord::Migration[5.1]
  def change
    rename_column :comment_tree_paths,:ancestor,:ancestor_id
    rename_column :comment_tree_paths,:descendant,:descendant_id
    add_column :comment_tree_paths,:parent_id,:integer
    add_column :comment_tree_paths,:descendant_type,:string
  end
end
