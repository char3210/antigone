package me.char321.antigone.mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StriderEntity.class)
public abstract class StriderEntityMixin extends AnimalEntity implements ItemSteerable, Saddleable {

    protected StriderEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(
            method = "initialize",
            at = @At(
                    value = "INVOKE",
                    // newly spawned passenger initialization
                    target = "Lnet/minecraft/entity/mob/MobEntity;initialize(" +
                            "Lnet/minecraft/world/WorldAccess;" +
                            "Lnet/minecraft/world/LocalDifficulty;" +
                            "Lnet/minecraft/entity/SpawnReason;" +
                            "Lnet/minecraft/entity/EntityData;" +
                            "Lnet/minecraft/nbt/CompoundTag;" +
                            ")Lnet/minecraft/entity/EntityData;"
            )
    )
    public EntityData addZombieData(MobEntity instance, WorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CompoundTag entityTag) {
        if (instance instanceof ZombifiedPiglinEntity && entityData == null) {
            // check if this is happening during chunk generation
            if (getServer() != null && Thread.currentThread() != getServer().getThread()) {
                // disable chicken jockey for this zombified piglin
                entityData = new ZombieEntity.ZombieData(ZombieEntity.method_29936(this.random), false);
            }
        }
        return instance.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }
}
