package net.lab0.tools;


public enum PowerOfTwoInteger
{
    _2(2),
    _4(4),
    _8(8),
    _16(16),
    _32(32),
    _64(64),
    _128(128),
    _256(256),
    _512(512),
    _1Ki(1_024),
    _2Ki(2_048),
    _4Ki(4_096),
    _8Ki(8_192),
    _16Ki(16_384),
    _32Ki(32_768),
    _64Ki(65_536),
    _128Ki(131_072),
    _256Ki(262_144),
    _512Ki(524_288),
    _1Mi(1_048_576),
    _2Mi(2_097_152),
    _4Mi(4_194_304),
    _8Mi(8_388_608),
    _16Mi(16_777_216),
    _32Mi(33_554_432),
    _64Mi(67_108_864),
    _128Mi(134_217_728),
    _256Mi(268_435_456),
    _512Mi(536_870_912),
    _1Gi(1_073_741_824),
    ;
    
    public final int value;
    
    private PowerOfTwoInteger(int value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "" + HumanReadable.humanReadableSizeInBytes(value);
    }
}
