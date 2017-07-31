class CreateAuthTokenTable < ActiveRecord::Migration[5.1]
  def change
    create_table :auth_tokens do |t|
      t.string :token
      t.integer :user_id
      t.datetime :expires_at
      
      t.timestamps null: false
    end
  end
end
