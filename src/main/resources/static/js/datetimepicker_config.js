/**
 * Prerequisite : js.datetimepicker.full.js
 *  Requires 2 input elements containing Ids as "startDateInfo" and "endDateInfo" respectively
 * */

jQuery(function(){

    jQuery('#startDateInfoForDayOrMonth').datetimepicker({
        timepicker: false,
        format:'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                maxDate:jQuery('#endDateInfoForDayOrMonth').val()?jQuery('#endDateInfoForDayOrMonth').val():false
            })
        },
        onSelectDate: function(ct,$i){
            let y = ct.getFullYear();
            let m = ct.getMonth();
            let ed = new Date(y, m, 1);
            if(m < 10){m ='0'+m;}


            $('#startDateInfoForDayOrMonth').val(y+'-'+m+'-'+ed.getDate());
        }
    });
    jQuery('#endDateInfoForDayOrMonth').datetimepicker({
        timepicker: false,
        format:'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                minDate:jQuery('#startDateInfoForDayOrMonth').val()?jQuery('#startDateInfoForDayOrMonth').val():false
            })
        },
        onSelectDate: function(ct,$i){
            let y = ct.getFullYear();
            let m = ct.getMonth()+1;
            let ed = new Date(y, m, 1);
            ed.setDate(ed.getDate()-1);
            if(m < 10){m ='0'+m;}

            $('#endDateInfoForDayOrMonth').val(y+'-'+m+'-'+ed.getDate());
        }
    });

    jQuery('#startDateInfo').datetimepicker({
        timepicker: false,
        format:'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                maxDate:jQuery('#endDateInfo').val()?jQuery('#endDateInfo').val():false
            })
        }
    });
    jQuery('#endDateInfo').datetimepicker({
        timepicker: false,
        format:'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                minDate:jQuery('#startDateInfo').val()?jQuery('#startDateInfo').val():false
            })
        }
    });
});