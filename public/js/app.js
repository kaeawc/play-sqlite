$('input#create').on('mousedown',function() {
  var name = $('input#create-name').val();

  if (!name) return;

  var options = {
    url: '/widget',
    method: 'POST',
    data: {
      "name" : name
    },
    success: function(data,status,xhr) {
      console.log('created successfully');

      if (data.created)
        select(data.created);
    },
    error: function(xhr,status,ex) {
      console.log('created unsuccessfully');
      console.log('status: ' + status);
      console.log('ex: ' + ex);
    }
  };

  $.ajax(options);

});

function select(id) {

  if (!id) {
    $('#widget-selected').hide();
    $('#no-widget-selected').show();
    return;
  }
  
  $('.search-results').hide();

  var options = {
    url: '/widget/' + id,
    method: 'GET',
    success: function(data,status,xhr) {
      console.log('got widget');

      $('#widget-id').html(id);
      $('#widget-name').html(data.name);
      $('#widget-created').html(new Date(data.created));

      $('#no-widget-selected').hide();
      $('#widget-selected').show();
    },
    error: function(xhr,status,ex) {
      console.log('no widget found');
      $('#widget-selected').hide();
      $('#no-widget-selected').show();
    }
  };

  $.ajax(options);
};

$('input#search-name').on('keydown',function() {

  setTimeout(function() {

    $('.search-results').html('');

    $('.search-results').hide();

    var name = $('input#search-name').val();

    if (!name) return;

    var options = {
      url: '/widget/find/' + name,
      method: 'GET',
      success: function(data,status,xhr) {
        console.log('searched successfully');

        var n = data.length;

        $('.search-results').show();

        for (var i = 0; i < n; i++) {
          $('.search-results').append("<div class='search-result' onclick='select(\"" + data[i].id + "\");'>" + data[i].name + "</div>");
        }
      },
      error: function(xhr,status,ex) {
        console.log('searched unsuccessfully');
        console.log('status: ' + status);
        console.log('ex: ' + ex);
      }
    };

    $.ajax(options);
  },100);
});