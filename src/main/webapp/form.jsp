<!--
Copyright 2016 Google Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->
<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
  <h3>
    <c:out value="${action}" /> a  <c:out value="${type}" /> Post
  </h3>

  <form method="POST" action="${destination}">

    <div class="form-group">
      <label for="title">Title</label>
      <input type="text" name="title" id="title" value="${fn:escapeXml(post.title)}" class="form-control" />
    </div>

    <div class="form-group">
      <label for="author">Author</label>
      <input type="text" name="author" id="author" value="${fn:escapeXml(post.author)}" class="form-control" />
    </div>

    <div class="form-group">
      <label for="publishedDate">Date Published</label>
      <input type="text" name="publishedDate" id="publishedDate" value="${fn:escapeXml(post.publishedDate)}" class="form-control" />
    </div>

    <div class="form-group">
      <label for="description">Description</label>
      <textarea name="description" id="description" class="form-control">${fn:escapeXml(post.description)}</textarea>
    </div>

    <div class="form-group ${isCloudStorageConfigured ? '' : 'hidden'}">
      <label for="image">Cover Image</label>
      <input type="file" name="file" id="file" class="form-control" />
    </div>

    <c:choose>
        <c:when test="${type == 'Schedule'}">
            <div class="form-group">
                <label for="publishedDate">Date and time of the Event</label>
                <input type="date" name="dateAndTime" id="dateAndTime" value="${fn:escapeXml(post.dateAndTime)}" class="form-control" />
            </div>
            <div class="form-group">
                <label for="publishedDate">Place</label>
                <input type="text" name="place" id="place" value="${fn:escapeXml(post.place)}" class="form-control" />
            </div>
        </c:when>
        <c:when test="${type == 'Survey'}">
            Teste ignorável
        </c:when>
        <c:otherwise>
            <c:out value="That's all" />
        </c:otherwise>
    </c:choose>

    <div class="form-group hidden">
      <label for="imageUrl">Cover Image URL</label>
      <input type="hidden" name="id" value="${post.id}" />
      <input type="text" name="imageUrl" id="imageUrl" value="${fn:escapeXml(post.imageUrl)}" class="form-control" />
    </div>

    <button type="submit" class="btn btn-success">Save</button>
  </form>
</div>
<!-- [END form] -->
