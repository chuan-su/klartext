class RenamePostTranslationToInterpretation < ActiveRecord::Migration[5.1]
  def change
    rename_column :posts,:translation,:interpretation
  end
end
