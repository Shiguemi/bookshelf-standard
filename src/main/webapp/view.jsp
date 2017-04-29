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
<!-- [START view] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="container">
  <h3>Post</h3>
  <div class="btn-group">
    <a href="/update?id=${post.id}" class="btn btn-primary btn-sm">
      <i class="glyphicon glyphicon-edit"></i>
      Edit post
    </a>
    <a href="/delete?id=${post.id}" class="btn btn-danger btn-sm">
      <i class="glyphicon glyphicon-trash"></i>
      Delete post
    </a>
  </div>

  <div class="media">
    <div class="media-left">
      <img class="post-image" src="${fn:escapeXml(not empty post.imageUrl?post.imageUrl:'http://placekitten.com/g/128/192')}">
    </div>
    <div class="media-body">
      <h4 class="post-title">
        ${fn:escapeXml(post.title)}
        <small>${fn:escapeXml(post.publishedDate)}</small>
      </h4>
      <h5 class="post-author">By ${fn:escapeXml(not empty post.author?post.author:'Unknown')}</h5>
      <p class="post-description">${fn:escapeXml(post.description)}</p>
      <small class="post-added-by">Added by
        ${fn:escapeXml(not empty post.createdBy?post.createdBy:'Anonymous')}</small>
    </div>
  </div>
</div>
<!-- [END view] -->
