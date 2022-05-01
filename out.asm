	.data
	newLine: .asciiz "\n"
	varx: .word 0
	vary: .word 0
	varcount: .word 0
	.text 0x00400000
	.globl main
main:
	li $v0, 2
	la $t0, varx
	sw $v0, ($t0)
	la $t0, varx
	lw $v0, ($t0)
	subu $sp, $sp, 4
	sw $v0, ($sp)
	li $v0, 1
	lw $t0, ($sp)
	addu $sp, $sp, 4
	addu $v0, $t0, $v0
	la $t0, vary
	sw $v0, ($t0)
	la $t0, varx
	lw $v0, ($t0)
	subu $sp, $sp, 4
	sw $v0, ($sp)
	la $t0, vary
	lw $v0, ($t0)
	lw $t0, ($sp)
	addu $sp, $sp, 4
	addu $v0, $t0, $v0
	la $t0, varx
	sw $v0, ($t0)
	la $t0, varx
	lw $v0, ($t0)
	subu $sp, $sp, 4
	sw $v0, ($sp)
	la $t0, vary
	lw $v0, ($t0)
	lw $t0, ($sp)
	addu $sp, $sp, 4
	mult $t0, $v0
	mflo $v0
	move $a0, $v0
	li $v0, 1
	syscall
	la $a0, newLine
	li $v0, 4
	syscall
	la $t0, varx
	lw $v0, ($t0)
	subu $sp, $sp, 4
	sw $v0, ($sp)
	la $t0, vary
	lw $v0, ($t0)
	lw $t0, ($sp)
	addu $sp, $sp, 4
	ble $t0, $v0, ifafter1
	j ifthen1
ifthen1:
	la $t0, varx
	lw $v0, ($t0)
	move $a0, $v0
	li $v0, 1
	syscall
	la $a0, newLine
	li $v0, 4
	syscall
	la $t0, vary
	lw $v0, ($t0)
	move $a0, $v0
	li $v0, 1
	syscall
	la $a0, newLine
	li $v0, 4
	syscall
	j ifafter1
ifafter1:
	li $v0, 14
	subu $sp, $sp, 4
	sw $v0, ($sp)
	li $v0, 14
	lw $t0, ($sp)
	addu $sp, $sp, 4
	bne $t0, $v0, ifafter2
	j ifthen2
ifthen2:
	li $v0, 14
	subu $sp, $sp, 4
	sw $v0, ($sp)
	li $v0, 14
	lw $t0, ($sp)
	addu $sp, $sp, 4
	beq $t0, $v0, ifafter3
	j ifthen3
ifthen3:
	li $v0, 3
	move $a0, $v0
	li $v0, 1
	syscall
	la $a0, newLine
	li $v0, 4
	syscall
	j ifafter3
ifafter3:
	li $v0, 14
	subu $sp, $sp, 4
	sw $v0, ($sp)
	li $v0, 14
	lw $t0, ($sp)
	addu $sp, $sp, 4
	bgt $t0, $v0, ifafter4
	j ifthen4
ifthen4:
	li $v0, 4
	move $a0, $v0
	li $v0, 1
	syscall
	la $a0, newLine
	li $v0, 4
	syscall
	j ifafter4
ifafter4:
	j ifafter2
ifafter2:
	li $v0, 15
	subu $sp, $sp, 4
	sw $v0, ($sp)
	li $v0, 14
	lw $t0, ($sp)
	addu $sp, $sp, 4
	ble $t0, $v0, ifafter5
	j ifthen5
ifthen5:
	li $v0, 5
	move $a0, $v0
	li $v0, 1
	syscall
	la $a0, newLine
	li $v0, 4
	syscall
	j ifafter5
ifafter5:
	li $v0, 1
	la $t0, varcount
	sw $v0, ($t0)
	j whileLoop6
whileLoop6:
	la $t0, varcount
	lw $v0, ($t0)
	subu $sp, $sp, 4
	sw $v0, ($sp)
	li $v0, 15
	lw $t0, ($sp)
	addu $sp, $sp, 4
	bgt $t0, $v0, endWhileLoop6
	la $t0, varcount
	lw $v0, ($t0)
	move $a0, $v0
	li $v0, 1
	syscall
	la $a0, newLine
	li $v0, 4
	syscall
	la $t0, varcount
	lw $v0, ($t0)
	subu $sp, $sp, 4
	sw $v0, ($sp)
	li $v0, 1
	lw $t0, ($sp)
	addu $sp, $sp, 4
	addu $v0, $t0, $v0
	la $t0, varcount
	sw $v0, ($t0)
	j whileLoop6
endWhileLoop6:
	li $v0, 10
	syscall
