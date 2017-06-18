class RenamePostInterpretationToInterp < ActiveRecord::Migration[5.1]
  def change
    rename_column :posts,:interpretation,:interp
  end
end
