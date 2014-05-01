/*
 * The MIT License
 *
 * Copyright 2011 Tushar Joshi, Nagpur.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.nbtaskfocus.core.ui.actions;

import com.nbtaskfocus.core.ui.ContextManager;
import com.nbtaskfocus.model.api.TaskItem;
import com.nbtaskfocus.model.api.TaskList;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.AbstractAction;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

/**
 *
 * @author Tushar Joshi <tusharvjoshi@gmail.com>
 */
public class RemoveTaskItemAction extends AbstractAction {

    private final TaskItem taskItem;
    private final String TASK_NAME = NbBundle.getMessage(RemoveTaskItemAction.class, "MNU_RemoveTaskItem");

    public RemoveTaskItemAction(TaskItem taskItem) {
        this.taskItem = taskItem;
        putValue(NAME, TASK_NAME);
    }

    @Override
    public void actionPerformed(ActionEvent e) {     
        taskItem.setSticky(false);
        ContextManager.getDefault().deactivateTaskItem(taskItem);

        TaskList taskList = TaskList.getDefault();

        String itemPath = taskItem.getItemPath();
        String projectPath = null;
        try {
            Project project = FileOwnerQuery.getOwner(new URI("file://"+itemPath));
            if (null != project) {
                projectPath = project.getProjectDirectory().getPath();
            }
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }

        

        taskList.removeContextItem(itemPath, projectPath);
    }
    
}
