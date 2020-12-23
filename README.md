# conference-testtask

Web-app available on http://localhost:8080/

Initial account logins are:
<p>admin
<p>presenter
<p>presenter2
<p>presenter3
<p>listener
<p>listener2
<p>(password equals login for initial accounts)

Input placeholders and main page contain hints

DateTime format is "dd-MM-yyyy HH:mm"
Choosing supporting presenters requires to input space separated login sequence
Choosing rooms requires to input room name string

Input validation mostly not implemented, so please be careful with input
Yet, presentation schedule protected from overlaping (as it required by tech task)

Presentation-Schedule one-to-one relation merged into single entity PresentationSchedule
Each Presentation-Schedule may have any number of supporting presenters and one main presenter who allowed to edit presentation info

Promoting listeners to presenters implemented as a part of user edit page (admin only)
Joining presentations implemented as a part of main menu (listeners only)
