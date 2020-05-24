import com.intellij.ide.BrowserUtil;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;

public class SearchAction extends AnAction
{
    @Override
    public void actionPerformed(AnActionEvent e)
    {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();

        String languageTag = "";
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        if(file != null)
        {
            Language lang = e.getData(CommonDataKeys.PSI_FILE).getLanguage();
            languageTag = "+[" + lang.getDisplayName().toLowerCase() + "]";
        }

        if(caretModel.getCurrentCaret().hasSelection())
        {
            String query = caretModel.getCurrentCaret().getSelectedText().replace(' ', '+');
            BrowserUtil.browse("https://stackoverflow.com/search?q=" + query);
        }
    }

    /**
     * Only make this action visible when text is selected.
     * @param e
     */
    @Override
    public void update(AnActionEvent e)
    {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        e.getPresentation().setEnabledAndVisible(caretModel.getCurrentCaret().hasSelection());
    }
}
