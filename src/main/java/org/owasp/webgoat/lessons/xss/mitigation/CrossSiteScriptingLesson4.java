/*
 * This file is part of WebGoat, an Open Web Application Security Project utility. For details, please see http://www.owasp.org/
 *
 * Copyright (c) 2002 - 2019 Bruce Mayhew
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * Getting Source ==============
 *
 * Source for this application is maintained at https://github.com/WebGoat/WebGoat, a repository for free software projects.
 */

package org.owasp.webgoat.lessons.xss.mitigation;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(value = {"xss-mitigation-4-hint1"})
public class CrossSiteScriptingLesson4 implements AssignmentEndpoint {

  @PostMapping("/CrossSiteScripting/attack4")
  @ResponseBody
  public AttackResult completed(@RequestParam String editor2) {

    String editor = editor2.replaceAll("\\<.*?>", "");

    if ((editor.contains("Policy.getInstance(\"antisamy-slashdot.xml\"")
            || editor.contains(".scan(newComment, \"antisamy-slashdot.xml\"")
            || editor.contains(".scan(newComment, new File(\"antisamy-slashdot.xml\")"))
        && editor.contains("new AntiSamy();")
        && editor.contains(".scan(newComment,")
        && editor.contains("CleanResults")
        && editor.contains("MyCommentDAO.addComment(threadID, userID")
        && editor.contains(".getCleanHTML());")) {
      return success(this).feedback("xss-mitigation-4-success").build();
    } else {
      return failed(this).feedback("xss-mitigation-4-failed").build();
    }
  }
}
