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
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import java.io.IOException;



// [START example]
@SuppressWarnings("serial")
public class UploadImageServlet extends HttpServlet {

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

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

      Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
      List<BlobKey> blobKeys = blobs.get("myFile");

      if (blobKeys == null || blobKeys.isEmpty()) {
          resp.getWriter().write("NOTOK");
      } else {
          resp.getWriter().write(blobKeys.toArray()[0].toString());
      }
  }
  // [END formpost]
}
// [END example]
