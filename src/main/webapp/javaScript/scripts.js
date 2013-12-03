/**************************************************************/
/* Prepares the cv to be dynamically expandable/collapsible   */
/**************************************************************/
function prepareList() {
    $('#expList').find('li:has(ul)').click(function(event) {
        //If line item is expandable but has no line items then call the service to retrieve data
        if ($(event.target).hasClass('collapsed') && $(event.target).find('li').length == 0) {
            $(event.target).children('ul').append("Add New LI's")
            $('#expList').find('li:has(ul)').addClass('collapsed');
            $('#expList').find('ul').addClass('inputslist');
            $(event.target).find('li:has(input)').unbind();
            $(event.target).children('ul').hide();
        }

        //Toggle the lists if they show or not
        $(event.target).toggleClass('expanded');
        $(event.target).children('ul').slideToggle('medium');
    }).addClass('collapsed').removeClass('expanded').children('ul').hide();

    //Create the button funtionality
    $('#expandList')
    .unbind('click')
    .click( function() {
        $('.collapsed').addClass('expanded');
        $('.collapsed').children().show('medium');
    })
    $('#collapseList')
    .unbind('click')
    .click( function() {
        $('.collapsed').removeClass('expanded');
        $('.collapsed').children().hide('medium');
    })



};

/**************************************************************/
/* Functions to execute on loading the document               */
/**************************************************************/
$(document).ready( function() {
    prepareList()
});
