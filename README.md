# Документация

## Описание существующей системы
Изначально существующее монолитное решение позволяет получать информацию о системе отопления (включена/выключена, текущая температура, целевая температура),
включать/выключать конкретную систему, устанавливать для нее целевую температуру, и получать текущую температуру.

## Домены и контексты
В рамках маленького монолитного приложения можно выделить домен "Управление устройствами".
В рамках этого домена можно выделить пару поддоменов: "Управление отоплением" и "Мониторинг температуры датчиков".
В рамках поддомена "Управление отоплением" выделяется аналогичный контекст с центральной сущностью "система отопления".
В рамках поддомена "Мониторинг температуры датчиков" выделается контекст "Считывание текущей температуры".

## Диаграммы
Диаграммы системы лежат в папке /schemas.  
system_context.puml - описывает диаграмму контекстов, что совпадает с изначальным состоянием монолитной системы.  
system_containers.puml - описывает диаграмму контейнеров целевой системы. Взаимодействие происходит как в синхронном,
так и в асинхронном формате.  
system_components.puml - описывает диаграмму компонентов целевой системы.  
ER.puml - описывает диаграмму сущностей БД целевой системы.

## Описание API
Документация на API основных компонентов системы лежит в папке /apis.

## НОВЫЕ МИКРОСЕРВИСЫ
Исходный код требумемыех новых микросервисов «Управление телеметрией» и «Управление устройствами» находятся в директориях /telemetry-service и /device-service, соответственно.
Helm-чарты для них находятся в директориях /charts/telemetry-service и /charts/device-service.
Helm-чарт для запуска всего приложения целиком находится в директории /charts/smart-home-app.

## Маршрутизация запросов API Gateway Kusk
Конфигурация маршрутизации запросов ко всем микросервиснам и монолиту описана в файле api.yaml в корневом каталоге проекта.

# Базовая настройка

## Запуск minikube

[Инструкция по установке](https://minikube.sigs.k8s.io/docs/start/)

```bash
minikube start
```


## Добавление токена авторизации GitHub

[Получение токена](https://github.com/settings/tokens/new)

```bash
kubectl create secret docker-registry ghcr --docker-server=https://ghcr.io --docker-username=<github_username> --docker-password=<github_token> -n default
```

## Установка приложения через Helm

```bash
cd charts/device-service
helm dependency build

cd charts/smart-home-app
helm dependency build
helm install <app-name> .
```


## Установка API GW kusk

[Install Kusk CLI](https://docs.kusk.io/getting-started/install-kusk-cli)

```bash
kusk cluster install
```


## Настройка terraform

[Установите Terraform](https://yandex.cloud/ru/docs/tutorials/infrastructure-management/terraform-quickstart#install-terraform)


Создайте файл ~/.terraformrc

```hcl
provider_installation {
  network_mirror {
    url = "https://terraform-mirror.yandexcloud.net/"
    include = ["registry.terraform.io/*/*"]
  }
  direct {
    exclude = ["registry.terraform.io/*/*"]
  }
}
```

## Применяем terraform конфигурацию 

```bash
cd terraform
terraform apply
```

## Настройка API GW

```bash
kusk deploy -i api.yaml
```

## Проверяем работоспособность

```bash
kubectl port-forward svc/kusk-gateway-envoy-fleet -n kusk-system 8080:80
curl localhost:8080/hello
```


## Delete minikube

```bash
minikube delete
```
