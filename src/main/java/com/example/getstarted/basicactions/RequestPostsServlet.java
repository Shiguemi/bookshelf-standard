/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.getstarted.basicactions;

import com.example.getstarted.daos.BookDao;
import com.example.getstarted.daos.ScheduleDao;
import com.example.getstarted.objects.Book;
import com.example.getstarted.objects.Schedule;
import com.google.appengine.repackaged.com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// [START example]
@SuppressWarnings("serial")
public class RequestPostsServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
      ServletException {

    try {

//      Schedule schedule;
//      schedule = new Schedule.Builder()
//              .title("Lucas test")
//              .build();

      String postType = req.getParameter("postType");
      System.out.println(postType);
      String json = "";
      switch (postType)
      {
        case "SCHEDULE":{
          ScheduleDao dao = (ScheduleDao) this.getServletContext().getAttribute("dao");
          List<Schedule> posts = dao.listSchedules("").result;
          json = new Gson().toJson(posts);
          break;
        }
        default:{

          BookDao dao = (BookDao) this.getServletContext().getAttribute("dao");
          List<Book> posts = dao.listBooks("").result;
          json = new Gson().toJson(posts);
          break;
        }
      }

      System.out.println("Post in JSON: " + json);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.getWriter().write(json);
      System.out.println("Post in JSON: " + json);

    } catch (Exception e) {
      throw new ServletException("Error reading Post", e);
    }
  }
}
// [END example]

