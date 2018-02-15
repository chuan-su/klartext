class RenameCommentTreePathDepthToLength < ActiveRecord::Migration[5.1]
  def change
    rename_column :comment_tree_paths,:depth,:length
  end
end
