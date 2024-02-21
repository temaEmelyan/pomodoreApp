# pomodoreApp

Simple pomodoro clock with user authentication and log keeping. 

Working version should be available at http://pomodoreapp.com

Create projects you are working on and add tasks for them. Track the time you spent working on every task using pomodoro timer technique.

![alt text](https://i.imgur.com/yYvmHJE.png)

![alt text](https://i.imgur.com/ivndAqM.png)

The app built using Spring-Boot 2, Spring-Security, Spring-Data-JPA, Maven and JQuery on the front-end. 

# Local Docker builds

The Dockerfile now will build the app locally.  This makes it easy for local development.  To build and run, for example

```
$ docker build -t pomodoreapp:latest .
$ docker run -p 8888:8080 pomodoreapp:latest
```
Change the line in the Dockerfile to switch between profiles (e.g. default, develop, postgres).  A future enhancement will be to parameterize these

```
ENV POMO_PROFILE="default"
```

# Kubernetes Manifest

You can use the `k8s.yaml` to launch into kubernetes

You are welcome to use the image saved, but otherwise, change the 'image' line to your container registry.  E.g. my working build in dockerhub:
```
image: idjohnson/pomodoreapp:0.0.3
```

The storageClass name is "default", but if you need a different one, change that as well.  For instance, mine is:
```
storageClassName: managed-nfs-storage
```

The Ingress block, if you use it, is setup for Nginx.  You'll want to configure your cluster-issuer for ACME (Lets Encrypt)
```
    cert-manager.io/cluster-issuer: letsencrypt-prod
```

And the hosts:
```
pomodoreapp.exmaple.com
```

# Some gaps

Data persistence is not quite there so this does not survive pod cycling. That will be addressed in future enhancements.


