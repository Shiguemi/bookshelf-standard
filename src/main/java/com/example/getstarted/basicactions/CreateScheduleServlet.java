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

import com.example.getstarted.daos.ScheduleDao;
import com.example.getstarted.objects.Schedule;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


// [START example]
@SuppressWarnings("serial")
public class CreateScheduleServlet extends HttpServlet {

  // [START setup]
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    req.setAttribute("action", "Add");          // Part of the Header in form.jsp
    req.setAttribute("destination", "createSchedule");  // The urlPattern to invoke (this Servlet)
    req.setAttribute("page", "form");           // Tells base.jsp to include form.jsp
    req.setAttribute("type", "Schedule");
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
  // [END setup]

  // [START formpost]
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
      // [START scheduleBuilder]
      Schedule schedule = null;

      if (req.getHeader("Content-Type").equals("application/json"))
      {
          try {
              BufferedReader reader = req.getReader();
              Gson gson = new GsonBuilder().create();
              schedule = gson.fromJson(reader, Schedule.class);
          } catch (Exception e)
          {
              System.err.println(e.getMessage());
          }
      } else {
          schedule = new Schedule.Builder()
                  .author(req.getParameter("author"))   // form parameter
                  .description(req.getParameter("description"))
                  .publishedDate(req.getParameter("publishedDate"))
                  .title(req.getParameter("title"))
                  .place(req.getParameter("place"))
                  .dateAndTime(req.getParameter("dateAndTime"))
                  .imageUrl(null)
                  .build();
      }
    // [END scheduleBuilder]

    ScheduleDao dao = (ScheduleDao) this.getServletContext().getAttribute("dao");
    try {
      Long id = dao.createSchedule(schedule);
      req.setAttribute("feedback", "OK");
    } catch (Exception e) {
      req.setAttribute("feedback", "NOTOK");
      throw new ServletException("Error creating schedule", e);
    }
  }
  // [END formpost]
}
// [END example]
