package me.rida.anticheat.data;

import com.google.common.collect.Lists;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.List;

public class DataPlayer {
	
    public boolean alerts = false;
    public double fallDistance = 0D;
    public float aboveBlockTicks = 0;
    public float waterTicks = 0;
    public long LastBlockPlacedTicks = 0;
    public boolean LastBlockPlaced_GroundSpoof = false;
    public float airTicks = 0;
    public float groundTicks = 0;
    public boolean ShouldSetBack = false;
    public float setBackTicks = 0;
    public long LastVelMS = 0;
    public boolean DidTakeVelocity = false;
    public long lastDelayedPacket;
    public long lastPlayerPacket;
    public Location setbackLocation;
    public double GoingUp_Blocks;
    public double LastY_Gravity;
    public float Gravity_VL;
    public float AntiCactus_VL;
    public double lastVelocityFlyY = 0;
    public double lastKillauraPitch = 0;
    public double lastKillauraYaw = 0;
    public long lastPacket = 0;
    public long lastAimTime = System.currentTimeMillis();
    public long Speed_Ticks = 0;
    public boolean Speed_TicksSet = false;
    public boolean isNearIce = false;
    public long isNearIceTicks = 0;
    public long LastVelUpdate = 0;
    public boolean LastVelUpdateBoolean = false;
    public double lastKillauraYawDif = 0;
    public long lastPacketTimer = 0;
    public long LastTimeTimer = 0;
    public float LastPACKETSTimer = 0;
    public long WebFloatMS = 0;
    public boolean WebFloatMS_Set = false;
    public float WebFloat_BlockCount = 0;
    public long AboveSpeedTicks = 0;
    public boolean AboveSpeedSet = false;
    public long HalfBlocks_MS = 0;
    public boolean HalfBlocks_MS_Set = false;
    public boolean Speed_C_2_Set = false;
    public long Speed_C_2_MS = 0;
    public long GlideTicks = 0;
    public long Speed_PistonExpand_MS = 0;
    public boolean Speed_PistonExpand_Set = false;
    public long BlockAbove = 0;
    public boolean BlockAbove_Set = false;
    public long Speed_YPORT_MS = 0;
    public boolean Speed_YPORT_Set = false;
    public float iceTicks = 0;
    public long Speed_YPort2_MS = 0;
    public boolean Speed_YPort2_Set = false;
    public long speedGroundReset = 0;
    public float slimeTicks = 0;
    
    public Player player;
    public boolean onGround, inLiquid, onStairSlab, onIce, onClimbable, underBlock;
    public float liquidTicks, blockTicks;
    public long lastVelocityTaken, lastAttack;
    public LivingEntity lastHitEntity;

    public List<Float> patterns = Lists.newArrayList();
    public float lastRange;

    public float speedThreshold;

    public DataPlayer(Player p) {
        this.player = p;
    }

    public float criticalsVerbose = 0;
    public float flyHoverVerbose = 0;
    public float flyVelocityVerbose = 0;
    public float GroundSpoofVL = 0;
    public float killauraAVerbose = 0;
    public float Speed2Verbose = 0;
    public float Speed_OnGround_Verbose = 0;
    public float TimerVerbose = 0;
    public float SpeedAC2_Verbose = 0;
    public float SpeedC_Verbose = 0;
    public float Speed_C_3_Verbose = 0;
    public float Speed_YPORT_Verbose = 0;
    public float Speed_YPort2_Verbose = 0;
    public float NEWSpeed_Verbose = 0;
    public float speedAVerbose = 0;
    public float Speed_C3_Verbose = 0;
    public float Jesus_Verbose = 0;

    public float getJesus_Verbose() {
        return Jesus_Verbose;
    }

    public void setJesus_Verbose(float jesus_Verbose) {
        Jesus_Verbose = jesus_Verbose;
    }

    public float getSpeed_C3_Verbose() {
        return Speed_C3_Verbose;
    }

    public void setSpeed_C3_Verbose(float speed_C3_Verbose) {
        Speed_C3_Verbose = speed_C3_Verbose;
    }

    public float getNEWSpeed_Verbose() {
        return NEWSpeed_Verbose;
    }

    public void setNEWSpeed_Verbose(float NEWSpeed_Verbose) {
        this.NEWSpeed_Verbose = NEWSpeed_Verbose;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isAlerts() {
        return alerts;
    }

    public void setAlerts(boolean alerts) {
        this.alerts = alerts;
    }

    public double getFallDistance() {
        return fallDistance;
    }

    public void setFallDistance(double fallDistance) {
        this.fallDistance = fallDistance;
    }

    public float getIceTicks() {
        return iceTicks;
    }

    public void setIceTicks(float iceTicks) {
        this.iceTicks = iceTicks;
    }

    public float getAboveBlockTicks() {
        return aboveBlockTicks;
    }

    public void setAboveBlockTicks(float aboveBlockTicks) {
        this.aboveBlockTicks = aboveBlockTicks;
    }

    public float getWaterTicks() {
        return waterTicks;
    }

    public void setWaterTicks(float waterTicks) {
        this.waterTicks = waterTicks;
    }

    public float getSpeedAVerbose() {
        return speedAVerbose;
    }

    public void setSpeedAVerbose(float speedAVerbose) {
        this.speedAVerbose = speedAVerbose;
    }

    public float getSlimeTicks() {
        return slimeTicks;
    }

    public void setSlimeTicks(float slimeTicks) {
        this.slimeTicks = slimeTicks;
    }

    public long getSpeedGroundReset() {
        return speedGroundReset;
    }

    public void setSpeedGroundReset(long speedGroundReset) {
        this.speedGroundReset = speedGroundReset;
    }

    public float getCriticalsVerbose() {
        return criticalsVerbose;
    }

    public void setCriticalsVerbose(float criticalsVerbose) {
        this.criticalsVerbose = criticalsVerbose;
    }

    public double getLastKillauraYawDif() {
        return lastKillauraYawDif;
    }

    public void setLastKillauraYawDif(double lastKillauraYawDif) {
        this.lastKillauraYawDif = lastKillauraYawDif;
    }

    public double getLastKillauraPitch() {
        return lastKillauraPitch;
    }

    public void setLastKillauraPitch(double lastKillauraPitch) {
        this.lastKillauraPitch = lastKillauraPitch;
    }

    public double getLastKillauraYaw() {
        return lastKillauraYaw;
    }

    public void setLastKillauraYaw(double lastKillauraYaw) {
        this.lastKillauraYaw = lastKillauraYaw;
    }

    public float getKillauraAVerbose() {
        return killauraAVerbose;
    }

    public void setKillauraAVerbose(float killauraAVerbose) {
        this.killauraAVerbose = killauraAVerbose;
    }

    public long getLastPacket() {
        return lastPacket;
    }

    public void setLastPacket(long lastPacket) {
        this.lastPacket = lastPacket;
    }

    public long getLastAimTime() {
        return lastAimTime;
    }

    public void setLastAimTime(long lastAimTime) {
        this.lastAimTime = lastAimTime;
    }

    public long getLastBlockPlacedTicks() {
        return LastBlockPlacedTicks;
    }

    public void setLastBlockPlacedTicks(long lastBlockPlacedTicks) {
        LastBlockPlacedTicks = lastBlockPlacedTicks;
    }

    public boolean isLastBlockPlaced_GroundSpoof() {
        return LastBlockPlaced_GroundSpoof;
    }

    public void setLastBlockPlaced_GroundSpoof(boolean lastBlockPlaced_GroundSpoof) {
        LastBlockPlaced_GroundSpoof = lastBlockPlaced_GroundSpoof;
    }
    public float getAirTicks() {
        return airTicks;
    }

    public void setAirTicks(float airTicks) {
        this.airTicks = airTicks;
    }

    public float getGroundTicks() {
        return groundTicks;
    }

    public void setGroundTicks(float groundTicks) {
        this.groundTicks = groundTicks;
    }

    public float getFlyHoverVerbose() {
        return flyHoverVerbose;
    }

    public void setFlyHoverVerbose(float flyHoverVerbose) {
        this.flyHoverVerbose = flyHoverVerbose;
    }

    public float getGroundSpoofVL() {
        return GroundSpoofVL;
    }

    public void setGroundSpoofVL(float groundSpoofVL) {
        GroundSpoofVL = groundSpoofVL;
    }

    public boolean isShouldSetBack() {
        return ShouldSetBack;
    }

    public void setShouldSetBack(boolean shouldSetBack) {
        ShouldSetBack = shouldSetBack;
    }

    public double getLastVelocityFlyY() {
        return lastVelocityFlyY;
    }

    public void setLastVelocityFlyY(double lastVelocityFlyY) {
        this.lastVelocityFlyY = lastVelocityFlyY;
    }

    public float getSetBackTicks() {
        return setBackTicks;
    }

    public void setSetBackTicks(float setBackTicks) {
        this.setBackTicks = setBackTicks;
    }

    public long getLastVelMS() {
        return LastVelMS;
    }

    public void setLastVelMS(long lastVelMS) {
        LastVelMS = lastVelMS;
    }

    public boolean isDidTakeVelocity() {
        return DidTakeVelocity;
    }

    public void setDidTakeVelocity(boolean didTakeVelocity) {
        DidTakeVelocity = didTakeVelocity;
    }

    public float getFlyVelocityVerbose() {
        return flyVelocityVerbose;
    }

    public void setFlyVelocityVerbose(float flyVelocityVerbose) {
        this.flyVelocityVerbose = flyVelocityVerbose;
    }

    public long getLastDelayedPacket() {
        return this.lastDelayedPacket;
    }

    public void setLastDelayedPacket(long l) {
        this.lastDelayedPacket = l;
    }


    public long getLastPlayerPacketDiff() {
        return System.currentTimeMillis() - this.getLastPlayerPacket();
    }

    public long getLastPlayerPacket() {
        return this.lastPlayerPacket;
    }
    public void setLastPlayerPacket(long l) {
        this.lastPlayerPacket = l;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getSetbackLocation() {
        return setbackLocation;
    }

    public void setSetbackLocation(Location setbackLocation) {
        this.setbackLocation = setbackLocation;
    }

    public double getGoingUp_Blocks() {
        return GoingUp_Blocks;
    }

    public void setGoingUp_Blocks(double goingUp_Blocks) {
        GoingUp_Blocks = goingUp_Blocks;
    }

    public double getLastY_Gravity() {
        return LastY_Gravity;
    }

    public void setLastY_Gravity(double lastY_Gravity) {
        LastY_Gravity = lastY_Gravity;
    }

    public float getGravity_VL() {
        return Gravity_VL;
    }

    public void setGravity_VL(float gravity_VL) {
        Gravity_VL = gravity_VL;
    }

    public float getAntiCactus_VL() {
        return AntiCactus_VL;
    }

    public void setAntiCactus_VL(float antiCactus_VL) {
        AntiCactus_VL = antiCactus_VL;
    }

    public long getSpeed_Ticks() {
        return Speed_Ticks;
    }

    public boolean isSpeed_TicksSet() {
        return Speed_TicksSet;
    }

    public void setSpeed_TicksSet(boolean speed_TicksSet) {
        Speed_TicksSet = speed_TicksSet;
    }

    public boolean isNearIce() {
        return isNearIce;
    }

    public void setNearIce(boolean nearIce) {
        isNearIce = nearIce;
    }

    public long getIsNearIceTicks() {
        return isNearIceTicks;
    }

    public void setIsNearIceTicks(long isNearIceTicks) {
        this.isNearIceTicks = isNearIceTicks;
    }

    public long getLastVelUpdate() {
        return LastVelUpdate;
    }

    public void setLastVelUpdate(long lastVelUpdate) {
        LastVelUpdate = lastVelUpdate;
    }

    public boolean isLastVelUpdateBoolean() {
        return LastVelUpdateBoolean;
    }

    public void setLastVelUpdateBoolean(boolean lastVelUpdateBoolean) {
        LastVelUpdateBoolean = lastVelUpdateBoolean;
    }

    public float getSpeed2Verbose() {
        return Speed2Verbose;
    }

    public void setSpeed2Verbose(float speed2Verbose) {
        Speed2Verbose = speed2Verbose;
    }


    public float getSpeed_OnGround_Verbose() {
        return Speed_OnGround_Verbose;
    }

    public void setSpeed_OnGround_Verbose(float speed_OnGround_Verbose) {
        Speed_OnGround_Verbose = speed_OnGround_Verbose;
    }

    public long getLastPacketTimer() {
        return lastPacketTimer;
    }

    public void setLastPacketTimer(long lastPacketTimer) {
        this.lastPacketTimer = lastPacketTimer;
    }

    public long getLastTimeTimer() {
        return LastTimeTimer;
    }

    public void setLastTimeTimer(long lastTimeTimer) {
        LastTimeTimer = lastTimeTimer;
    }

    public float getTimerVerbose() {
        return TimerVerbose;
    }

    public void setTimerVerbose(float timerVerbose) {
        TimerVerbose = timerVerbose;
    }

    public float getLastPACKETSTimer() {
        return LastPACKETSTimer;
    }

    public void setLastPACKETSTimer(float lastPACKETSTimer) {
        LastPACKETSTimer = lastPACKETSTimer;
    }

    public long getWebFloatMS() {
        return WebFloatMS;
    }

    public void setWebFloatMS(long webFloatMS) {
        WebFloatMS = webFloatMS;
    }

    public boolean isWebFloatMS_Set() {
        return WebFloatMS_Set;
    }

    public void setWebFloatMS_Set(boolean webFloatMS_Set) {
        WebFloatMS_Set = webFloatMS_Set;
    }

    public float getWebFloat_BlockCount() {
        return WebFloat_BlockCount;
    }

    public void setWebFloat_BlockCount(float webFloat_BlockCount) {
        WebFloat_BlockCount = webFloat_BlockCount;
    }

    public long getAboveSpeedTicks() {
        return AboveSpeedTicks;
    }

    public void setAboveSpeedTicks(long aboveSpeedTicks) {
        AboveSpeedTicks = aboveSpeedTicks;
    }

    public boolean isAboveSpeedSet() {
        return AboveSpeedSet;
    }

    public void setAboveSpeedSet(boolean aboveSpeedSet) {
        AboveSpeedSet = aboveSpeedSet;
    }

    public float getSpeedAC2_Verbose() {
        return SpeedAC2_Verbose;
    }

    public void setSpeedAC2_Verbose(float speedAC2_Verbose) {
        SpeedAC2_Verbose = speedAC2_Verbose;
    }

    public long getHalfBlocks_MS() {
        return HalfBlocks_MS;
    }

    public void setHalfBlocks_MS(long halfBlocks_MS) {
        HalfBlocks_MS = halfBlocks_MS;
    }

    public boolean isHalfBlocks_MS_Set() {
        return HalfBlocks_MS_Set;
    }

    public void setHalfBlocks_MS_Set(boolean halfBlocks_MS_Set) {
        HalfBlocks_MS_Set = halfBlocks_MS_Set;
    }

    public boolean isSpeed_C_2_Set() {
        return Speed_C_2_Set;
    }

    public void setSpeed_C_2_Set(boolean speed_C_2_Set) {
        Speed_C_2_Set = speed_C_2_Set;
    }

    public long getSpeed_C_2_MS() {
        return Speed_C_2_MS;
    }

    public void setSpeed_C_2_MS(long speed_C_2_MS) {
        Speed_C_2_MS = speed_C_2_MS;
    }

    public float getSpeedC_Verbose() {
        return SpeedC_Verbose;
    }

    public void setSpeedC_Verbose(float speedC_Verbose) {
        SpeedC_Verbose = speedC_Verbose;
    }

    public long getGlideTicks() {
        return GlideTicks;
    }

    public void setGlideTicks(long glideTicks) {
        GlideTicks = glideTicks;
    }

    public float getSpeed_C_3_Verbose() {
        return Speed_C_3_Verbose;
    }

    public void setSpeed_C_3_Verbose(float speed_C_3_Verbose) {
        Speed_C_3_Verbose = speed_C_3_Verbose;
    }

    public long getSpeed_PistonExpand_MS() {
        return Speed_PistonExpand_MS;
    }

    public void setSpeed_PistonExpand_MS(long speed_PistonExpand_MS) {
        Speed_PistonExpand_MS = speed_PistonExpand_MS;
    }

    public boolean isSpeed_PistonExpand_Set() {
        return Speed_PistonExpand_Set;
    }

    public void setSpeed_PistonExpand_Set(boolean speed_PistonExpand_Set) {
        Speed_PistonExpand_Set = speed_PistonExpand_Set;
    }

    public long getBlockAbove() {
        return BlockAbove;
    }

    public boolean isBlockAbove_Set() {
        return BlockAbove_Set;
    }

    public void setBlockAbove(long blockAbove) {
        BlockAbove = blockAbove;
    }

    public void setBlockAbove_Set(boolean blockAbove_Set) {
        BlockAbove_Set = blockAbove_Set;
    }

    public float getSpeed_YPORT_Verbose() {
        return Speed_YPORT_Verbose;
    }

    public void setSpeed_YPORT_Verbose(float speed_YPORT_Verbose) {
        Speed_YPORT_Verbose = speed_YPORT_Verbose;
    }

    public long getSpeed_YPORT_MS() {
        return Speed_YPORT_MS;
    }

    public void setSpeed_YPORT_MS(long speed_YPORT_MS) {
        Speed_YPORT_MS = speed_YPORT_MS;
    }

    public boolean isSpeed_YPORT_Set() {
        return Speed_YPORT_Set;
    }

    public void setSpeed_YPORT_Set(boolean speed_YPORT_Set) {
        Speed_YPORT_Set = speed_YPORT_Set;
    }

    public long getSpeed_YPort2_MS() {
        return Speed_YPort2_MS;
    }

    public void setSpeed_YPort2_MS(long speed_YPort2_MS) {
        Speed_YPort2_MS = speed_YPort2_MS;
    }

    public boolean isSpeed_YPort2_Set() {
        return Speed_YPort2_Set;
    }

    public void setSpeed_YPort2_Set(boolean speed_YPort2_Set) {
        Speed_YPort2_Set = speed_YPort2_Set;
    }

    public float getSpeed_YPort2_Verbose() {
        return Speed_YPort2_Verbose;
    }

    public void setSpeed_YPort2_Verbose(float speed_YPort2_Verbose) {
        Speed_YPort2_Verbose = speed_YPort2_Verbose;
    }
}