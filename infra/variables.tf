variable "project_name" {
  description = "Project name — used as resource prefix and tag"
  type        = string
}

variable "region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

variable "instance_type" {
  description = "EC2 instance type (t3.medium for JVM + PostgreSQL)"
  type        = string
  default     = "t3.medium"
}

variable "ssh_public_key" {
  description = "RSA public key material for EC2 key pair (injected by platform)"
  type        = string
}

variable "vpc_cidr" {
  description = "CIDR block for the VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "public_subnet_cidr" {
  description = "CIDR block for the public subnet"
  type        = string
  default     = "10.0.1.0/24"
}
