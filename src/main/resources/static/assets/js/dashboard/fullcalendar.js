var fullCalendar;
$().ready(function () {

    fullCalendar = $('#m_calendar').fullCalendar({
        locale: 'zh-cn',
        events: {
            url: contextPath + "fullcalendar/events",
            type: 'GET',
            beforeSend: function () {

            },
            data: {
                beginDate: "2017-10-01",
                endDate: "2017-11-31"
            },
            error: function () {
                alert('there was an error while fetching events!');
            },
            color: 'yellow',   // a non-ajax option
            textColor: 'black' // a non-ajax option
        }
    });
    var beginDate = fullCalendar.fullCalendar('getView').start ;
    var endDate = fullCalendar.fullCalendar('getView').end ;
    Logger.debug(beginDate.format());
    Logger.debug(endDate.format());



})
