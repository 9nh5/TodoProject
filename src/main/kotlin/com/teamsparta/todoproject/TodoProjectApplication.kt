package com.teamsparta.todoproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@SpringBootApplication
class TodoProjectApplication

fun main(args: Array<String>) {
    runApplication<TodoProjectApplication>(*args)
}
