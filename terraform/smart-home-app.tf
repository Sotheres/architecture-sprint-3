resource "helm_release" "smart-home-app" {
  name       = "smart-home-app"
  namespace  = "default"
  chart      = "../charts/smart-home-app"
}