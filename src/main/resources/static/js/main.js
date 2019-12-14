$(document).ready(function() {
    feather.replace();
});

let lastRateYo = null;


function openMovieReviewDetailsModal(movieReviewId) {
    $.LoadingOverlay("show");
    $.ajax({
        url: "/user/my-reviews/" + movieReviewId,
        success: function (data) {
            $('#myReviewsModalHolder').html(data);
            $.LoadingOverlay("hide");
            $('#movieReviewDetailsModal').modal('show');
        },
        error: function(data) {
            $.LoadingOverlay("hide");
        }
    })
}

function openMovieReviewDeleteModal(movieReviewId) {
    $.LoadingOverlay("show");
    $.ajax({
        url: "/user/my-reviews/" + movieReviewId + "/delete",
        success: function (data) {
            $('#myReviewsModalHolder').html(data);
            $.LoadingOverlay("hide");
            $('#movieReviewDeleteModal').modal('show');
        },
        error: function(data) {
            $.LoadingOverlay("hide");
        }
    })
}

function openMovieReviewEditModal(movieReviewId) {
    $.LoadingOverlay("show");
    $.ajax({
        url: "/reviews/" + movieReviewId + "/edit",
        success: function (data) {
            $('#myReviewsModalHolder').html(data);
            $.LoadingOverlay("hide");
            $('#movieReviewEditModal').modal('show');
        },
        error: function(data) {
            $.LoadingOverlay("hide");
        }
    })
}

function initializeUserMovieReviewsTableStarRatings() {
    $(".movie-review-row").each(function(index) {

        const movieReviewId = $(this).data('movie-review-id');
        const userMovieReviewRating = $(this).data('user-movie-review-rating');
        $("#user-movie-review-rating-table-" + movieReviewId).rateYo({
            rating: userMovieReviewRating,
            readOnly: true
        });
    });
}

function initializeUserMovieReviewDetailsModal() {
    $(function () {
        const movieReviewId = $('#movieReviewDetailsModal').data('movie-review-id');
        const movieRating = $('#movieReviewDetailsModal').data('user-movie-rating');
        $("#user-movie-review-rating-modal-" + movieReviewId).rateYo({
            rating: movieRating,
            readOnly: true
        });
    });
}

function initializeUserMovieReviewEditModal() {
    $(function () {

        initializeEditMovieReviewModalRateYo();
        initializeEditMovieReviewModalForm();

    });

}

function initializeEditMovieReviewModalForm() {
    $("#movieReviewEditForm").submit(function (event) {
        event.preventDefault();
        const postUrl = $(this).attr("action");
        const postMethod = $(this).attr("method");
        const formData = $(this).serialize();


        $.ajax({
            url: postUrl,
            type: postMethod,
            data: formData,
        }).done(function (response) {
            window.location = "/user/my-reviews";
        }).fail(function (response) {
            let respDom = $(response.responseText);
            let formDom = $('#movieReviewEditForm', respDom);
            $('#movieReviewEditModal').html(formDom.parent().html());
            initializeEditMovieReviewModalRateYo();
        });

    });
}

function initializeEditMovieReviewModalRateYo() {
    if(lastRateYo != null) {
        lastRateYo.rateYo("destroy");
        lastRateYo = null;
    }

    const movieReviewId = $('#movieReviewEditModal').data('movie-review-id');
    const movieRating = $('#movieReviewEditModal').data('user-movie-rating');
    lastRateYo = $("#user-movie-review-rating-edit-modal-" + movieReviewId).rateYo({
        rating: movieRating,
        halfStar: true

    });

    lastRateYo.rateYo()
        .on("rateyo.change", function (e, data) {

            var rating = data.rating;
            $(this).next().text(rating);
            $('#userRating').val(rating);
        });
}