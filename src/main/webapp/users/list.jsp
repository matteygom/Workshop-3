<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<jsp:include page="../header.jsp"/>

<div id="content-wrapper" class="d-flex flex-column">

  <div id="content">

    <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
    </nav>

    <div class="container-fluid">

      <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>
        <a href="<c:url value="/users/addUser.jsp"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
          <i class="fas fa-download fa-sm text-white-50"></i> Dodaj użytkownika</a>
      </div>
      <div class="card shadow mb-4">
        <div class="card-header py-3">
          <h6 class="m-0 font-weight-bold text-primary">Lista użytkowników</h6>
        </div>
        <div class="card-body">
          <div class="table-responsive">
            <table class="table">
              <tr>
                <th>Id</th>
                <th>Nazwa użytkownika</th>
                <th>Email</th>
                <th>Akcja</th>
              </tr>
              <c:forEach items="${usersList}" var="user">
                <tr>
                  <td>${user.id}</td>
                  <td>${user.userName}</td>
                  <td>${user.email}</td>
                  <td>
                    <a href='<c:url value="/user/delete?id=${user.id}"/>'>Usuń</a>
                    <a href='<c:url value="/user/edit?id=${user.id}"/>'>Edit</a>
                    <a href='<c:url value="/user/show?id=${user.id}"/>'>Pokaż</a>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<jsp:include page="../footer.jsp"/>