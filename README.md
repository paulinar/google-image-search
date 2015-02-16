# Google Image Search
A Google image search client for Android.

Time spent: 16 hours spent in total

## Completed user stories:

 * [x] User can enter a search query that will display a grid of image results from the Google Image API.
 * [x] User can click on "settings" which allows selection of advanced search options to filter results
 * [x] User can configure advanced search filters such as size, color filter, type, site
 * [x] Subsequent searches will have any filters applied to the search results
 * [x] User can tap on any image in results to see the image full-screen
 * [x] User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
 * [x] *Optional*: Robust error handling, check if internet is available, handle error cases, network failures
 * [x] *Optional*: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
 * [x] *Optional*: Replace Filter Settings Activity with a lightweight modal overlay
 * [x] *Optional*: Use the StaggeredGridView to display improve the grid of image results
 * [x] *Optional*: User can zoom or pan images displayed in full-screen detail view

Also implemented ViewHolder pattern for fast lookups.

## Walkthrough of all user stories

![Video Walkthrough](walkthrough.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Libraries

This app leverages two third-party libraries:

 * [Android AsyncHTTPClient](http://loopj.com/android-async-http/) - For asynchronous network requests
 * [Picasso](http://square.github.io/picasso/) - For remote image loading