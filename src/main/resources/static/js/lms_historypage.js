/**
 * parameter "by" should be "MONTH" OR "DAY"
 * */
function searchByDailyOrMonthly(by) {
    const cb = $('#callbackForDailyOrMonthly option:selected').val();
    const startDate = $('#startDateInfoForDayOrMonth').val();
    const endDate  = $('#endDateInfoForDayOrMonth').val();

    const form = $('<form action="' + historylogurl + '" method="get">' +
        '<input type="text" name="session" value="' + session + '" />' +
        '<input type="text" name="access" value="' + access + '" />' +
        '<input type="text" name="callback" value="' + cb + '" />' +
        '<input type="text" name="dailyOrMonthly" value="' + by + '" />' +
        '<input type="text" name="startDate" value="' + startDate + '" />' +
        '<input type="text" name="endDate" value="' + endDate + '" />' +
        '</form>');

    $('body').append(form);
    form.submit();
}


/**
 *
 * */
function searchByDate() {
    const cb = $('#callbackForDateSearch option:selected').val();
    const startDate = $('#startDateInfo').val();
    const endDate  = $('#endDateInfo').val();

    const form = $('<form action="' + historyurl + '" method="get">' +
        '<input type="text" name="session" value="' + session + '" />' +
        '<input type="text" name="access" value="' + access + '" />' +
        '<input type="text" name="callback" value="' + cb + '" />' +
        '<input type="text" name="startDate" value="' + startDate + '" />' +
        '<input type="text" name="endDate" value="' + endDate + '" />' +
        '</form>');

    $('body').append(form);
    form.submit();
}