class CreateCommentTreePaths < ActiveRecord::Migration[5.1]
  def change
    create_table :comment_tree_paths do |t|
      t.integer :ancestor
      t.integer :descendant
      t.integer :depth
      t.integer :post_id
      t.timestamps null: false
    end
  end
end
