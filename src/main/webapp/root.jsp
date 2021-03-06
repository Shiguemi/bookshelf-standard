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
<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>]

<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '194943897656179',
      xfbml      : true,
      version    : 'v2.9'
    });
    FB.AppEvents.logPageView();
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));

   FB.getLoginStatus(function(response) {
     if (response.status === 'connected') {
       alert("Logged");
       var uid = response.authResponse.userID;
       var accessToken = response.authResponse.accessToken;
     } else if (response.status === 'not_authorized') {
       alert("Not logged in app");
     } else {
       alert("Not logged in facebook");
     }
    }, true);

    FB.logout(function(response) {
       // Person is now logged out
    });
</script>




<div class="container">
  <h3>Post</h3>
    <a href="/create" class="btn btn-success btn-sm">
      <i class="glyphicon glyphicon-plus"></i>
      Add a test Book
    </a>
    <a href="/createSchedule" class="btn btn-success btn-sm">
      <i class="glyphicon glyphicon-plus"></i>
      Add Schedule
    </a>
    <a href="/list" class="btn btn-success btn-sm">
      <i class="glyphicon glyphicon-plus"></i>
      Nothing yet.
    </a>

  <h4> ${nPosts} </h4>
  <c:choose>
  <c:when test="${empty posts}">
  <p>No Posts found</p>
  </c:when>
  <c:otherwise>
  <c:forEach items="${posts}" var="post">
  <div class="media">
    <a href="/read?id=${post.id}">
      <div class="media-left">
        <img alt="ahhh" src="${fn:escapeXml(not empty post.imageUrl?post.imageUrl:'http://placekitten.com/g/128/192')}">
      </div>
      <div class="media-body">
        <h4>${fn:escapeXml(post.title)}</h4>
        <p>${fn:escapeXml(post.author)}</p>
      </div>
    </a>
  </div>
  </c:forEach>
  <c:if test="${not empty cursor}">
  <nav>
    <ul class="pager">
      <li><a href="?cursor=${fn:escapeXml(cursor)}">More</a></li>
    </ul>
  </nav>
  </c:if>
  </c:otherwise>
  </c:choose>
</div>
<button onclick="testar = FB.get; alert(testar);">Check Status</button>
<button onclick="FB.logout();">Sair do facebook</button>
<div class="fb-login-button" data-max-rows="1" data-size="large" data-button-type="continue_with" data-show-faces="false" data-auto-logout-link="false" data-use-continue-as="false"></div>

<!-- [END list] -->
