(function (ng) {
    var mod = ng.module("doctorModule");
    mod.controller("doctorsCtrl", ['$scope', '$state', '$stateParams', '$http', 'doctorContext', function ($scope, $state, $stateParams, $http, context) {

        loadDocs = function () {
            $http.get(context).then(function (response) {
                $scope.doctors = response.data;
            }, responseError);
        }

        loadDocs();

        this.deleteRecord = function (doc) {
            return $http.delete(context + "/" + doc)
                .then(function () {
                    loadDocs();
                }, responseError)
        }

        if ($stateParams.docID !== null && $stateParams.docID !== undefined) {

            // toma el id del parámetro
            id = $stateParams.docID;
            // obtiene el dato del recurso REST
            $http.get(context + "/" + id)
                .then(function (response) {
                    // $http.get es una promesa
                    // cuando llegue el dato, actualice currentRecord
                    var currentRecord = response.data;
                    $scope.nombre = currentRecord.name;
                    $scope.cedula = currentRecord.id;
                    $scope.especialidad = currentRecord.especialidad;
                    $scope.consultorio = currentRecord.consultorio;
                }, responseError);

            // el controlador no recibió un editorialId
        } else {
            // el registro actual debe estar vacio
            $scope.currentRecord = {
                id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                name: '' /*Tipo String*/
            };

            $scope.alerts = [];
        }

        this.saveDoctor = function () {
            if (!$scope.nombre || !$scope.especialidad || !$scope.consultorio || !$scope.cedula) {
                alert("No puede dejar ningún campo vacio.");
                return;
            }
            if (isNaN($scope.cedula)) {
                alert("La cédula debe ser numérica.");
                return;
            }
            if (isNaN($scope.consultorio)) {
                alert("El consultorio debe ser un número.");
                return;
            }
            else {
                var doc =
                {
                    "name": $scope.nombre,
                    "id": $scope.cedula,
                    "especialidad": $scope.especialidad,
                    "consultorio": $scope.consultorio
                };
                doc = JSON.stringify(doc);
                console.log(doc);
                return $http.post(context, doc.toString())
                    .then(function () {
                        $state.go('doctorsList');
                    }, responseError)
            }
        }

        this.editDoctorFinal = function () {
            if (!$scope.nombre || !$scope.especialidad || !$scope.consultorio || !$scope.cedula) {
                alert("No puede dejar ningún campo vacio.");
                return;
            }
            if (isNaN($scope.cedula)) {
                alert("La cédula debe ser numérica.");
                return;
            }
            if (isNaN($scope.consultorio)) {
                alert("El consultorio debe ser un número.");
                return;
            }
            else {
                var doc =
                {
                    "name": $scope.nombre,
                    "id": $scope.cedula,
                    "especialidad": $scope.especialidad,
                    "consultorio": $scope.consultorio
                };
                doc = JSON.stringify(doc);
                console.log(doc);
                return $http.put(context + "/" + $stateParams.docID, doc.toString())
                    .then(function () {
                        $state.go('doctorsList');
                    }, responseError)
            }
        }


        this.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };

        function showMessage(msg, type) {
            var types = ["info", "danger", "warning", "success"];
            if (types.some(function (rc) {
                    return type === rc;
                })) {
                $scope.alerts.push({type: type, msg: msg});
            }
        }

        this.showError = function (msg) {
            showMessage(msg, "danger");
        };

        this.showSuccess = function (msg) {
            showMessage(msg, "success");
        };

        var self = this;

        function responseError(response) {

            self.showError(response.data);
        }
    }]);

})(window.angular);