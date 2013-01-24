	<html>
	<head>
	<title>LinkedIn Login</title>
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
	    .fields(["id", "firstName", "lastName", "industry", "pictureUrl", "publicProfileUrl"])
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
    profHTML = "Your name: " + profile.firstName + profile.lastName + "<br>";
    profHTML += "Your ID: " + profile.id + "<br>";
    profHTML += "Your industry: " + profile.industry + "<br>";
    profHTML += "<a href=\"#\" onclick=\"IN.User.logout(); return false;\">logout</a></p>";
  }
  document.getElementById("loginbadge").innerHTML = profHTML;
}
</script>

<div id="loginbadge">
</div>
<br><br>
<a href="servlet/LinkedInLogin">Go to LinkedIn API script (work in progress)</a>

</body>
</html>
