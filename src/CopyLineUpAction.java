import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.openapi.util.TextRange;

import java.awt.*;

public class CopyLineUpAction extends EditorAction {

    public CopyLineUpAction(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }

    public CopyLineUpAction() {
        this(new UpHandler());
    }

    private static class UpHandler extends EditorWriteActionHandler {
        private UpHandler() {
        }

        @Override
        public void executeWriteAction(Editor editor, DataContext dataContext) {
            Document document = editor.getDocument();

            if (editor == null || document == null || !document.isWritable()) {
                return;
            }

            CaretModel caretModel = editor.getCaretModel();

            SelectionModel selectionModel = editor.getSelectionModel();

            TextRange charsRange = new TextRange(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd());

            TextRange linesRange = new TextRange(document.getLineNumber(charsRange.getStartOffset()), document.getLineNumber(charsRange.getEndOffset()));

            TextRange linesBlock = new TextRange(document.getLineStartOffset(linesRange.getStartOffset()), document.getLineEndOffset(linesRange.getEndOffset()));

            String duplicatedString = document.getText().substring(linesBlock.getStartOffset(), linesBlock.getEndOffset());
            duplicatedString += "\n";

            document.insertString(linesBlock.getStartOffset(), duplicatedString);

            editor.getSelectionModel().setSelection(linesBlock.getStartOffset(), linesBlock.getStartOffset());

            caretModel.moveToOffset(linesBlock.getStartOffset());
            editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
        }
    }
}
