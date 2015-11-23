<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		
		<title>api.spec() - Linode API</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Our API is a programmatic interface into many of the Linode Manager's features. The API has been expanded to include not only DNS Manager functions, but also Linode creation, control, and deletion.">
		<meta name="keywords" content="linode api, cloud server api, vps server api">
		
		

		
		<link href='//fonts.googleapis.com/css?family=Lato:300,400,700,900' rel='stylesheet' type='text/css'>
		<link href="/media/css/home.css?12" rel="stylesheet">

		<script src="/media/js/jquery.min.js?0"></script>
		<script src="//cdn.optimizely.com/js/2220190451.js"></script>

		
		

		
		

		<link rel="shortcut icon" href="/favicon.ico">
	</head>

	<body class="has-subnav" >
		<header>
			<nav id="main-nav" class="navbar navbar-default" role="navigation">
				<div class="container">

					<div class="navbar-header">
						<button type="button" class="toggle navbar-toggle" data-toggle="collapse" data-target=".navbar-top-collapse">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
						<a href="/"><img id="navbar-logo" src="/media/images/header/linode_logo_white.png"></a>
					</div>

					
						<div class="collapse navbar-collapse navbar-top-collapse">
							<ul class="nav navbar-nav navbar-right">

								<li class=""><a href="/"><span class='nav-home'></span></a></li> <li class=""><a href="/linodes">Features</a></li> <li class=""><a href="/pricing">Pricing</a></li> <li class=""><a href="/addons">Add-ons</a></li> 

								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">Resources <span class="caret"></span></a>
									<ul class="dropdown-menu dropdown-main-nav dropdown-mega">
										<li class="dropdown-third">
											<ul >
												<li><a href="/docs/getting-started">Getting Started</a></li>
												<li><a href="/docs/migrate-to-linode/migrate-from-shared-hosting">Migrating to Linode</a></li>
												<li><a href="/docs/websites/hosting-a-website">Hosting a Website</a></li>
												<li class="divider"></li>
												<li class="big"><a href="/docs"><i class="fa fa-book"></i> Guides &amp; Tutorials</a></li>
												<li class="divider visible-xs"></li>
											</ul>
										</li>
										<li class="dropdown-third middle">
											<ul >
												<li><a href="/api">API</a></li>
												<li><a href="/stackscripts">StackScripts</a></li>
												<li><a href="/mobile">Mobile</a></li>
												<li><a href="/cli" target="_blank">CLI</a></li>

												<li class="divider"></li>

												<li><a href="/chat"><i class="fa fa-bullhorn gray"></i> Chat</a></li>
												<li><a href="https://forum.linode.com"><i class="fa fa-comments"></i> Community Forum</a></li>
												<li class="divider visible-xs"></li>
											</ul>
										</li>
										<li class="dropdown-third">
											<ul >
												<li><a href="https://blog.linode.com">Blog</a></li>
												<li><a href="http://status.linode.com">System Status</a></li>
												<li><a href="/speedtest">Speed Test</a></li>
												<li><a href="/about">About Us</a></li>
												<li class="divider"></li>
												<li><a href="/contact"><i class="fa fa-user"></i> Contact Support</a></li>
											</ul>
										</li>
									</ul>
								</li>

								<li role="presentation" class="divider-vertical"><span>|</span></li>

								
									<li class=""><a href="https://manager.linode.com/">Log in <span class="login-caret"></span></a></li>
									<li class="visible-xs"><a href="https://manager.linode.com/session/signup">Sign up</a></li>
									
										<li class="hidden-xs"><div><a id="btn-signup-top" class="btn btn-white btn-sm navbar-btn hidden-xs" href="https://manager.linode.com/session/signup">Sign up</a></div></li>
									
							</ul>
						</div>
					


				</div>
			</nav>

			
			

				<nav class="navbar white subnav jumbotron" role="navigation">
					<div class="container">

						<div class="subnav-divider">
							<div class="navbar-header">
								
									<button type="button" class="toggle navbar-toggle" data-toggle="collapse" data-target=".navbar-sub-collapse">
										<span class="sr-only">Toggle sub navigation</span>
										<span class="toggle-closed glyphicon glyphicon-chevron-down"></span>
										<span class="toggle-open glyphicon glyphicon-remove"></span>
									</button>
								
									<h6 class="navbar-brand">
										<a href="/api">API Docs</a>
									</h6>
								
							</div>

							<div class="collapse navbar-collapse navbar-sub-collapse">
								<ul class="nav navbar-nav navbar-right">
									
												<li><a 
												href="/api">Intro</a></li>
											
												<li><a 
												href="/api/linode">Linode</a></li>
											
												<li><a 
												href="/api/nodebalancer">NodeBalancer</a></li>
											
												<li><a 
												href="/api/stackscript">StackScript</a></li>
											
												<li><a 
												href="/api/image">Image</a></li>
											
												<li><a 
												href="/api/dns">DNS</a></li>
											
												<li><a 
												href="/api/account">Account</a></li>
											
												<li><a class="active"
												href="/api/utility">Utility</a></li>
											
								</ul>
							</div>

						</div>

					</div>
				</nav>
			

		</header>

		

<section class="white doc">
	<div class="container">
		<div class="row">
			<div class="col-sm-8">
				<h1 class="h2">api.spec()</h1>
				<p>Returns a data structure of the entire Linode API specification.  This method does not require authorization.<br><br>For example: <a target="_blank" href="https://api.linode.com/?api_action=api.spec">https://api.linode.com/?api_action=api.spec</a></p>


				


				<h3>Example Response</h3>
				<pre class="highlight">
{
 "ERRORARRAY":[],
 "ACTION":"account.info",
 "DATA":{
  "METHODS":{
   "account.info":{
    "DESCRIPTION":"Shows information about your account such as
     the date your account was opened as well as your network
     utilization for the current month in gigabytes.",
     "PARAMETERS":{},
     "THROWS":""
    },
    "api.spec":{
    "DESCRIPTION" : "Returns a data structure of the entire
     Linode API specification.",
     "PARAMETERS":{},
     "THROWS":""
   }
  }
 }
}
</pre>


				<h3>Errors</h3>
				
					<p>None.</p>
				

			</div>
			<div class="col-sm-4">
				<div class="sidebar">
					<ul> <li><h5>API</h5><ul><li><a href='/api/utility/api.spec'>api.spec</a></li></ul></li> <li><h5>Avail</h5><ul><li><a href='/api/utility/avail.datacenters'>avail.datacenters</a></li><li><a href='/api/utility/avail.distributions'>avail.distributions</a></li><li><a href='/api/utility/avail.kernels'>avail.kernels</a></li><li><a href='/api/utility/avail.linodeplans'>avail.linodeplans</a></li><li><a href='/api/utility/avail.nodebalancers'>avail.nodebalancers</a></li><li><a href='/api/utility/avail.stackscripts'>avail.stackscripts</a></li></ul></li> <li><h5>Test</h5><ul><li><a href='/api/utility/test.echo'>test.echo</a></li></ul></li></ul>
				</div>
			</div>
		</div>
	</div>
</section>



		<footer>
			
				<section id="pre-footer">
					<div class="container">
						<div class="row">
							<div class="col-sm-7">
								<span>Get started in the Linode Cloud today.</span>
							</div>
							<div class="col-sm-5 pad-xs">
								<a id="btn-signup-bottom" class="btn btn-lg btn-full btn-white" href="https://manager.linode.com/session/signup">Create an Account</a>
							</div>
						</div>
					</div>
				</section>

				<section class="dark">
					<div class="container">

						<div class="row">
							<div class="footer-col">
								<h5><a href="/linodes">Overview</a></h5>
								<ul>
									<li><a href="/pricing">Plans &amp; Pricing</a></li>
									<li><a href="/linodes">Features</a></li>
									<li><a href="/backups">Backups</a></li>
									<li><a href="/nodebalancers">NodeBalancers</a></li>
									<li><a href="/longview">Longview</a></li>
									<li><a href="/managed">Managed</a></li>
									<li><a href="/professional-services">Professional Services</a></li>
									<li><a href="/stackscripts">StackScripts</a></li>
									<li><a href="/mobile">Mobile</a></li>
									<li><a href="/cli">CLI</a></li>
									<li><a href="/api">API</a></li>
								</ul>
							</div>

							<div class="footer-col">
								<h5><a href="/docs">Resources</a></h5>
								<ul>
									<li><a href="/docs/getting-started">Getting Started</a></li>
									<li><a href="/docs/migrate-to-linode/migrate-from-shared-hosting">Migrating to Linode</a></li>
									<li><a href="/docs/websites/hosting-a-website">Hosting a Website</a></li>
									<li><a href="/docs">Guides &amp; Tutorials</a></li>
									<li><a href="/compliance">Standards &amp; Compliance</a></li>
									<li><a href="/speedtest">Speed Test</a></li>
									<li><a href="https://forum.linode.com/">Forum</a></li>
									<li><a href="/chat">Chat</a></li>
									<li><a href="http://status.linode.com/">System Status</a></li>
								</ul>
							</div>


							<div class="footer-col">
								<h5><a href="/about">Company</a></h5>
								<ul>
									<li><a href="/about">About Us</a></li>
									<li><a href="/employees">Employees</a></li>
									<li><a href="https://blog.linode.com">Blog</a></li>
									<li><a href="/events">Events</a></li>
									<li><a href="/press">Press</a></li>
									<li><a href="/case-studies">Case Studies</a></li>
									<li><a href="/logos">Logos</a></li>
									<li><a href="/referrals">Referral System</a></li>
									<li><a href="/careers">Careers</a></li>
									<li><a href="/contact">Contact</a></li>
								</ul>
							</div>

							<div class="footer-col">
								<h5><a href="/contact">Contact Us</a></h5>
								<ul>
									<li><a href="tel:+18554546633">855-4-LINODE</a></li>
									<li><a href="tel:+18554546633">(855-454-6633)</a></li>
									<li><a href="mailto:support@linode.com">Email us</a></li>
									<li>
										<ul>
											<li><a target="_blank" href="https://facebook.com/linode"><i class="fa fa-facebook-square"></i> Facebook</a></li>
											<li><a target="_blank" href="https://twitter.com/linode"><i class="fa fa-twitter-square"></i> Twitter</a></li>
											<li><a target="_blank" href="https://plus.google.com/+linode/"><i class="fa fa-google-plus-square"></i> Google+</a></li>
											<li><a target="_blank" href="https://linkedin.com/company/linode"><i class="fa fa-linkedin-square"></i> Linkedin</a></li>
											<li><a target="_blank" href="https://github.com/linode/"><i class="fa fa-github-square"></i> Github</a></li>
										</ul>
									</li>
								</ul>
							</div>

						</div>

					</div>
				</section>
			

			<section class="dark-moar">
				<div class="container">
					<div id="footer-copyright" class="row">
						
							<div class="col-sm-3 text-center">
								&copy; 2015 Linode, LLC
							</div>

							<div class="col-sm-3 text-center">
								<a href="/tos">Terms of Service</a>
							</div>

							<div class="col-sm-3 text-center">
								<a href="/privacy">Privacy Policy</a>
							</div>

							<div class="col-sm-3 text-center">
								<a href="/security">Security</a>
							</div>
						
					</div>
				</div>
			</section>
		</footer>

		<script src="/media/js/bootstrap.min.js?0"></script>
		<script src="/media/js/prettify.min.js?0"></script>
		<script src="/media/js/jquery.syntaxhighlighter.min.js?0"></script>
		<script src="/media/js/jquery.transit.min.js?0"></script>

		<script src="/media/js/main.js?5"></script>

		<div class="t">
			<script type="text/javascript">
				var _gaq = _gaq || [];
				var pluginUrl = '//www.google-analytics.com/plugins/ga/inpage_linkid.js';
				_gaq.push(['_require', 'inpage_linkid', pluginUrl]);
				_gaq.push(['_setAccount', 'UA-177150-1']);
				_gaq.push(['_setDomainName', 'linode.com']);
				_gaq.push(['_trackPageview']);

				(function() {
					var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
					ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
					var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
				})();
			</script>

			<script type="text/javascript">
			/* <![CDATA[ */
			var google_conversion_id = 1071926901;
			var google_custom_params = window.google_tag_params;
			var google_remarketing_only = true;
			/* ]]> */
			</script>
			<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js">;
			</script>
			<noscript>
			<div style="display:inline;">
			<img height="1" width="1" style="border-style:none;" alt="" src="//googleads.g.doubleclick.net/pagead/viewthroughconversion/1071926901/?value=0&amp;guid=ON&amp;script=0"/>
			</div>
			</noscript>

			<script type="text/javascript">
				adroll_adv_id = "IL4WBB7BGVAMPP6O6DBOTR";
				adroll_pix_id = "PYYL6IEEX5AWZGO6IXQDLQ";
				(function () {
				var oldonload = window.onload;
				window.onload = function(){
					__adroll_loaded=true;
					var scr = document.createElement("script");
					var host = (("https:" == document.location.protocol) ? "https://s.adroll.com" : "http://a.adroll.com");
					scr.setAttribute('async', 'true');
					scr.type = "text/javascript";
					scr.src = host + "/j/roundtrip.js";
					((document.getElementsByTagName('head') || [null])[0] ||
					document.getElementsByTagName('script')[0].parentNode).appendChild(scr);
					if(oldonload){oldonload()}};
				}());
			</script>

			<script type="text/javascript">
				setTimeout(function(){var a=document.createElement("script");
				var b=document.getElementsByTagName("script")[0];
				a.src=document.location.protocol+"//dnn506yrbagrg.cloudfront.net/pages/scripts/0024/3657.js?"+Math.floor(new
				Date().getTime()/3600000);
				a.async=true;a.type="text/javascript";b.parentNode.insertBefore(a,b)}, 1);
			</script>

			

			<!-- Bing Ads Universal Event Tracker -->
			<script>(function(w,d,t,r,u){var f,n,i;w[u]=w[u]||[],f=function(){var o={ti:"4055942"};o.q=w[u],w[u]=new UET(o),w[u].push("pageLoad")},n=d.createElement(t),n.src=r,n.async=1,n.onload=n.onreadystatechange=function(){var s=this.readyState;s&&s!=="loaded"&&s!=="complete"||(f(),n.onload=n.onreadystatechange=null)},i=d.getElementsByTagName(t)[0],i.parentNode.insertBefore(n,i)})(window,document,"script","//bat.bing.com/bat.js","uetq");</script><noscript><img src="//bat.bing.com/action/0?ti=4055942&Ver=2" height="0" width="0" style="display:none; visibility: hidden;" /></noscript>
		</div>

	</body>
</html>
