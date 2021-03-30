/*
    Creating and return HTML element with classes
 */
function makeElement(type, classes) {
    const element = document.createElement(type);
    element.className = classes;
    return element;
}

/*
    Function to transform the rating of a comment
    into a colored and/or blank paws
 */
function drawRating(rating) {
    const container = makeElement("div", "rating-container");

    for (let i = 0; i < 5; i++) {
        if (i < rating) {
            container.appendChild(makeElement("div", "pet-hotel-rating pet-hotel-rating-positive"));
        } else {
            container.appendChild(makeElement("div", "pet-hotel-rating pet-hotel-rating-negative"));
        }
    }

    return container;
}

/*
    Function to create full comment in HTML
    you need to pass description, rating and author
 */
function buildComment(description, rating, author) {
    const container = makeElement("div", "container py-4");
    const row = makeElement("div", "row g-2 my-3");
    const authorBox = makeElement("div", "col-lg-6 col-md-6 col-sm-12 col-12 text-lg-center text-md-center text-start");
    const authorContent = makeElement("div", "px-3");
    const ratingBox = makeElement("div", "col-lg-6 col-md-6 col-sm-12 col-12 text-lg-center text-md-center text-start");
    const ratingContent = drawRating(rating);
    const descriptionBox = makeElement("div", "col-12");
    const descriptionContent = makeElement("div", "p-3 border bg-light text-start");

    authorContent.innerHTML = author;
    descriptionContent.innerHTML = description;

    authorBox.appendChild(authorContent);
    ratingBox.appendChild(ratingContent);
    descriptionBox.appendChild(descriptionContent);

    row.appendChild(authorBox);
    row.appendChild(ratingBox);
    row.appendChild(descriptionBox);

    container.appendChild(row);

    return container;
}

/*
    Specific function for creating buttons
    id, class and text are passed into the function
 */
function makeButton(id, classes, text) {
    const button = makeElement("button", classes);
    button.type = "button"
    button.id = id;
    button.innerHTML = text.toString();
    return button;
}

/*
    Function to fetch a page of comments from the REST controller
    Draws all the comments on the page
    along with the pagination buttons, adding necessary events
 */
async function fetchApprovedComments(page) {
    await fetch("http://localhost:8080/comments/pages?page=" + page.toString())
        .then((response) => response.json())
        .then((data) => {

            const container = document.getElementById("commentsContainer");
            const buttonsContainer = document.getElementById("commentsPageButtonsContainer");
            container.innerHTML = null;
            buttonsContainer.innerHTML = null;
            const pageHeader = document.getElementById("commentsPageHeader");

            if (data.comments.length > 0) {

                data.comments.forEach((comment) => {
                    const commentElement = buildComment(comment.description, comment.rating, comment.author);
                    container.appendChild(commentElement);
                });

            } else {
                pageHeader.innerHTML = "Все още нямаме отзиви. Може да оставите първия!"
            }

            if (data.totalPages > 1) {
                let currentPage = data.currentPage + 1;
                let pageIndex = data.currentPage;

                const currentPageButton = makeButton("currentPageOfComments", "btn btn-outline-secondary", currentPage);

                if (currentPage > 1) {
                    const firstPageButton = makeButton("firstPageOfComments", "btn btn-outline-secondary", "Начална");
                    firstPageButton.addEventListener("click", function () {
                        fetchApprovedComments(0);
                    });

                    const previousPageButton = makeButton("previousPageOfComments", "btn btn-outline-secondary", currentPage - 1);
                    previousPageButton.addEventListener("click", function () {
                        fetchApprovedComments(pageIndex - 1);
                    });

                    buttonsContainer.appendChild(firstPageButton);
                    buttonsContainer.appendChild(previousPageButton);
                }

                buttonsContainer.appendChild(currentPageButton);

                if (currentPage < data.totalPages) {
                    const nextPageButton = makeButton("nextPageOfComments", "btn btn-outline-secondary", currentPage + 1);
                    nextPageButton.addEventListener("click", function () {
                        fetchApprovedComments(pageIndex + 1);
                    });

                    const lastPageButton = makeButton("lastPageOfComments", "btn btn-outline-secondary", "Последна");
                    lastPageButton.addEventListener("click", function () {
                        fetchApprovedComments(data.totalPages - 1);
                    });

                    buttonsContainer.appendChild(nextPageButton);
                    buttonsContainer.appendChild(lastPageButton);
                }
            }
        })
    document.documentElement.scrollTop = 0;
}

/*
    Color the paws for the rating selection in the comment form
 */
$('input[type=radio][name=rating]').change(function () {
    const ratingInput = document.getElementsByName("rating");
    let found = false;
    for (let i = ratingInput.length - 1; i >= 0; i--) {
        if (ratingInput[i].checked) {
            found = true;
        }

        if (found) {
            ratingInput[i].className = "selectedRadioInput";
        } else {
            ratingInput[i].className = "";
        }
    }
});

/*
    Post a comment
 */
const postCommentButton = document.getElementById("postCommentButton");
const formContent = document.getElementById("petHotelCommentPostForm");

if (postCommentButton) {
    postCommentButton.addEventListener("click", function () {
        const rating = document.querySelector("input[name='rating']:checked");
        const description = document.getElementById("commentDescription");

        if (rating == null) {
            $("#ratingError").attr("hidden", false);
        } else {
            $("#ratingError").attr("hidden", true);
        }

        if (description.value === "") {
            $("#descriptionError").attr("hidden", false);
        } else {
            $("#descriptionError").attr("hidden", true);
        }

        if (rating == null || description.value === "") {
            return
        }

        formContent.innerHTML = "КОМЕНТАРЪТ СЕ КАЧВА";

        const comment = {
            "description": description.value,
            "rating": rating.value
        }

        formContent.innerHTML = null;
        const notification = makeElement("div", "pet-hotel-notification pet-hotel-notification-positive");
        notification.innerHTML = "КОМЕНТАРЪТ Е КАЧЕН И ЧАКА ОДОБРЕНИЕ ОТ АДМИНИСТРАТОР";
        formContent.appendChild(notification);

        fetch("http://localhost:8080/comments/add", {
            headers: {"Content-Type": "application/json"},
            method: "POST",
            body: JSON.stringify(comment)
        });
    });
}

/*
    Visualise the pending comments with two possible option buttons
    APPROVE and ARCHIVE
 */
async function fetchPendingComments() {
    await fetch("http://localhost:8080/comments/pending-comments")
        .then((response) => response.json())
        .then((data) => {

            const container = document.getElementById("pendingCommentsContainer");

            if (data.comments.length > 0) {

                data.comments.forEach((comment) => {
                    const commentContainer = makeElement("div", "container");
                    const commentElement = buildComment(comment.description, comment.rating, comment.author);

                    const approveButton = makeButton("approveButton", "mx-2 btn btn-outline-success pet-hotel-custom-btn-width", "Одобри");
                    const archiveButton = makeButton("archiveButton", "mx-2 btn btn-outline-warning pet-hotel-custom-btn-width", "Архивирай");
                    const buttonsGroup = makeElement("div", "container");

                    approveButton.addEventListener("click", async function () {
                        approveButton.innerHTML = "&#8987;";
                        await fetch("http://localhost:8080/comments/approve/" + comment.id,
                            {
                                method: "POST",
                                headers: {"Content-Type": "application/json"},
                                body: {}
                            });
                        commentContainer.remove();
                    });

                    archiveButton.addEventListener("click", async function () {
                        archiveButton.innerHTML = "&#8987;";
                        await fetch("http://localhost:8080/comments/archive/" + comment.id,
                            {
                                method: "POST",
                                headers: {"Content-Type": "application/json"},
                                body: {}
                            });
                        commentContainer.remove();
                    });

                    buttonsGroup.appendChild(approveButton);
                    buttonsGroup.appendChild(archiveButton);
                    commentContainer.appendChild(commentElement);
                    commentContainer.appendChild(buttonsGroup);

                    container.appendChild(commentContainer);
                });

            } else {
                const header = makeElement("div", "");
                header.innerHTML = "Няма чакащи за одобрение коментари";
                container.appendChild(header);
            }
        });
}

/*
    Visualise the archived comments with two possible option buttons
    APPROVE and DELETE
 */
async function fetchArchivedComments() {
    await fetch("http://localhost:8080/comments/archived-comments")
        .then((response) => response.json())
        .then((data) => {

            const container = document.getElementById("pendingCommentsContainer");

            if (data.comments.length > 0) {

                data.comments.forEach((comment) => {
                    const commentContainer = makeElement("div", "container");
                    const commentElement = buildComment(comment.description, comment.rating, comment.author);

                    const approveButton = makeButton("approveButton", "mx-2 btn btn-outline-success pet-hotel-custom-btn-width", "Одобри");
                    const deleteButton = makeButton("deleteButton", "mx-2 btn btn-outline-danger pet-hotel-custom-btn-width", "Изтрий");
                    const buttonsGroup = makeElement("div", "container");

                    approveButton.addEventListener("click", async function () {
                        approveButton.innerHTML = "&#8987;";
                        await fetch("http://localhost:8080/comments/approve/" + comment.id,
                            {
                                method: "POST",
                                headers: {"Content-Type": "application/json"},
                                body: {}
                            });
                        commentContainer.remove();
                    });

                    deleteButton.addEventListener("click", async function () {
                        deleteButton.innerHTML = "&#8987;";
                        await fetch("http://localhost:8080/comments/delete/" + comment.id,
                            {
                                method: "POST",
                                headers: {"Content-Type": "application/json"},
                                body: {}
                            });
                        commentContainer.remove();
                    });

                    buttonsGroup.appendChild(approveButton);
                    buttonsGroup.appendChild(deleteButton);
                    commentContainer.appendChild(commentElement);
                    commentContainer.appendChild(buttonsGroup);

                    container.appendChild(commentContainer);
                });

            } else {
                const header = makeElement("div", "");
                header.innerHTML = "Няма архивирани коментари";
                container.appendChild(header);
            }
        });
}