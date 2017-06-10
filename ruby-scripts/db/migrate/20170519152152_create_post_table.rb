class CreatePostTable < ActiveRecord::Migration[5.1]
  def change
    create_table :posts do |t|
      t.text :body
      t.integer :created_by

      t.timestamps null: false
    end
  end
end
