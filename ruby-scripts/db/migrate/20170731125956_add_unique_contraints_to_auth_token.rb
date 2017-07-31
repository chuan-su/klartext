class AddUniqueContraintsToAuthToken < ActiveRecord::Migration[5.1]
  def change
    add_index :auth_tokens, [:token,:user_id], unique: true
  end
end
