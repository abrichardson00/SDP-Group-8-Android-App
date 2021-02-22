# SDP-Group-8-Android-App

I'm just gonna give some information on the different api requests that the app should be able to make to the server - this seems like an appropriate place people can refer to.
We can do the following http requests to *{ip address}:5000/...*

**Getting tray info and images:**


*get request for /api/images/{tray_name}* -> get a specific tray image

*get request for /api/trays/{tray_name}* -> get a specific tray json

**Searching for an item:**

get request for /api/trays?search=blahblahblah -> we give some search string, we return list of all tray json objects but listed in order of appropriateness for the search
We can also supply no search string:

*get request for /api/trays?search=*   , or

*get request for /api/trays?search* -> get a list of all json objects, no search

**Updating tray info:**

*put request to /api/trays/{tray_name}*


One gives json string as a parameter, which is then used to update the appropriate tray for tray_name. Could literally give a complete tray json string as the parameter, or could just give something like {"info":"blahblah"} and this would only update the tray info for tray_name.
I've also implemented some checks here - the user can only update tray info or it's status (stored, moving or out). i.e. even if they provide a changed tray_name or capacity in the json parameter it won't do anything.

**Bringing / Storing trays:**

If one wants to bring a tray, we just update the tray info, giving the appropriate put request including our desired end state: {"status":"out"}
Similarly to store a tray, give a put request with {"status":"stored"}.

Requesting to bring a tray also handles finding whatever tray currently out, and setting it to 'stored'.
