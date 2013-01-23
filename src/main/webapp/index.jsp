<html>
<head>
<script type='text/javascript' src='http://platform.linkedin.com/in.js'>
api_key: 34eiwzgyjlyn
authorize: true
</script>
</head>
<body onload="onLinkedInLoad()">

<script type="text/javascript">
function onLinkedInLoad() {
  IN.Event.on(IN, "auth", function() {onLinkedInLogin();});
  IN.Event.on(IN, "logout", function() {onLinkedInLogout();});
}

function onLinkedInLogout() {
  setLoginBadge(false);
}

function onLinkedInLogin() {
  // we pass field selectors as a single parameter (array of strings)
  IN.API.Profile("me")
    .fields(["id", "firstName", "lastName", "pictureUrl", "publicProfileUrl"])
    .result(function(result) {
      setLoginBadge(result.values[0]);
    })
    .error(function(err) {
      alert(err);
    });
}

function setLoginBadge(profile) {
  if (!profile) {
    profHTML = "<p>You are not logged in</p>";
  }
  else {
    var pictureUrl = profile.pictureUrl || "http://static02.linkedin.com/scds/common/u/img/icon/icon_no_photo_80x80.png";
    profHTML = "<p><a href=\"" + profile.publicProfileUrl + "\">";
    profHTML = profHTML + "<img align=\"baseline\" src=\"" + pictureUrl + "\"></a>";      
    profHTML = profHTML + "&nbsp; Welcome <a href=\"" + profile.publicProfileUrl + "\">";
    profHTML = profHTML + profile.firstName + " " + profile.lastName + "</a>! <a href=\"#\" onclick=\"IN.User.logout(); return false;\">logout</a></p>";
  }
  document.getElementById("loginbadge").innerHTML = profHTML;
}
</script>

<!-- <a href="servlet/LinkedInLogin">Link to LinkedIn Login script</a> -->

<div id="loginbadge"><p></p>
</div>

</body>
</html>
