**Simple Note Taking App**
THIS APP IS COMPILED IN ANDROID STUDIO USING JAVA AND VARIOUS DPENDENCIES LIKE ROOM DATABASE, FIRESTORE DATABASE, AUTHENTICATION, RECYCLERVIEW

**UserGuide**
Firstly, the app needs a valid email id through with the verification link is sent to the recepient email box. Clicking on the link from the mail completes the verification process and user can now login to the app.
The layout is simple and you can add note from the Floating Action Button at the bottom corner of the page. Once created, the note becomes visible at the Home page. Users can click on the note to go to the edit page.
(The same page for creating the note is used for updating the note too.)
Along with the option to update, a delete button also appears in the view which on clicked prompts a confirmation dialog.
Similarly, a search feature is too implemented (for base -> title), it supports the text-change filter feature which allows users to get distinct results without cliking the ok or search button after inserting the search text.

**Problems**
The problems related with this task was learning a new database framework (Room Database). It was quite easier than the traditional SQLite Database, and executes the queries fast. Similarly, several bugs were fixed during
the assignment of this task, includes Search Operation from the Room Database, price declared at the note creation wasn't updating in the adapter, etc.
