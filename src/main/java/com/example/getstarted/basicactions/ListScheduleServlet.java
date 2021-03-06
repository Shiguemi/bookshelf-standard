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

import com.example.getstarted.daos.DatastoreDao;
import com.example.getstarted.daos.ScheduleDao;
import com.example.getstarted.objects.Result;
import com.example.getstarted.objects.Schedule;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// [START example]
@SuppressWarnings("serial")
public class ListScheduleServlet extends HttpServlet {

  @Override
  public void init() throws ServletException {
    DatastoreDao dao = null;

    // Creates the DAO based on the Context Parameters
    String storageType = this.getServletContext().getInitParameter("bookshelf.storageType");
    switch (storageType) {
      case "datastore":
        dao = new DatastoreDao();
        break;
      default:
        throw new IllegalStateException(
                "Invalid storage type. Check if bookshelf.storageType property is set.");
    }
    this.getServletContext().setAttribute("dao", dao);
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
          ServletException {
    ScheduleDao dao = (ScheduleDao) this.getServletContext().getAttribute("dao");
    String startCursor = req.getParameter("cursor");
    List<Schedule> schedules = null;
    String endCursor = null;
    try {
      Result<Schedule> result = dao.listSchedules(startCursor);
      schedules = result.result;
      endCursor = result.cursor;
    } catch (Exception e) {
      throw new ServletException("Error listing schedules", e);
    }
    req.getSession().getServletContext().setAttribute("posts", schedules);
    StringBuilder scheduleNames = new StringBuilder();
    for (Schedule schedule : schedules) {
      scheduleNames.append(schedule.getTitle() + " ");
    }
    req.setAttribute("cursor", endCursor);
    req.setAttribute("page", "list");
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}
// [END example]
